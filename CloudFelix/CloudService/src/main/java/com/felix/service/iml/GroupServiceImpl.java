package com.felix.service.iml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import com.felix.domain.Group;
import com.felix.domain.RelUserGroup;
import com.felix.service.GroupService;

@Singleton
public class GroupServiceImpl extends AbstractService<Group> implements GroupService{

	@Override
	public Iterable<Map<String, Object>> getGroupByUser() {
		String query = "MATCH s-[:REL_USER_GROUP]->(p:Group) return p, count(s) as buddies ORDER BY buddies DESC";
        return getSession().query(query, Collections.emptyMap());
	}

	@Override
	Class<Group> getEntityType() {
		return Group.class;
	}

	@Override
	public List<Group> findByUser(Long id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pUser", id);
		String query = "MATCH (s:User)-[:REL_USER_GROUP]->(p) where ID(s) = {pUser} return p";
		List<Group> result = new ArrayList<Group>();
		getSession().query(Group.class, query, params).forEach(result::add);
		return result;
	}

	@Override
	public List<RelUserGroup> findRelByUser(Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pUser", userId);
		String query = "MATCH (s:User)-[r:REL_USER_GROUP]->(p) where ID(s) = {pUser} return r";
		List<RelUserGroup> result = new ArrayList<RelUserGroup>();
		getSession().query(RelUserGroup.class, query, params).forEach(result::add);
		return result;
	}


}
