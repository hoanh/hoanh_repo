package com.felix.service.iml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.neo4j.ogm.transaction.Transaction;

import com.felix.domain.Group;
import com.felix.domain.RelUserGroup;
import com.felix.domain.User;
import com.felix.service.GroupService;
import com.felix.service.UserService;

@Singleton
public class UserServiceImpl extends GenericService<User> implements UserService{
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	 private @EJB GroupService groupService;
	@Override
	public Iterable<Map<String, Object>> getGroupByUser() {
		String query = "MATCH (s:User)<-[:REL_USER_GROUP]-(p:Group) return p, count(s) as buddies ORDER BY buddies DESC";
        return getSession().query(query, Collections.emptyMap());
	}
	
	@Override
	Class<User> getEntityType() {
		return User.class;
	}

	@Override
	public void update(User user, List<Group> selectedGroups) {
		Map<Long,RelUserGroup> mapGroup = new HashMap<Long,RelUserGroup>();
		Set<RelUserGroup> rugs = user.getRels();
		if(rugs != null) {
			Iterator<RelUserGroup> itr = rugs.iterator();
			while (itr.hasNext()) { 
				RelUserGroup rug = itr.next();
				mapGroup.put(rug.getGroup().getId(), rug);
			}
		}
		Transaction tx = getSession().beginTransaction();
		try{
			Set<RelUserGroup> groups = new HashSet<RelUserGroup>();
			RelUserGroup rel;
		    for (Group group : selectedGroups) {
		    	if(mapGroup.containsKey(group.getId())) {
		    		rel = mapGroup.remove(group.getId());
		    	}else {
		    		rel = new RelUserGroup(user, group);
		    	}
		    	groups.add(rel);
			}
		    user.setRels(groups);
		    for (RelUserGroup relUserGroup : mapGroup.values()) {
				getSession().delete(relUserGroup);
			}
		    createOrUpdate(user);
		    tx.commit();
		} catch (Exception e) {
		    tx.rollback();
		}finally {
			tx.close();
		}
		
	}

	@Override
	public void add(User user, List<Group> selectedGroups) {
		Transaction tx = getSession().beginTransaction();
		try{
			Set<RelUserGroup> groups = new HashSet<RelUserGroup>();
			RelUserGroup rel;
		    for (Group group : selectedGroups) {
				rel = new RelUserGroup(user, group);
				groups.add(rel);
			}
		    user.setRels(groups);
		    createOrUpdate(user);
		    tx.commit();
		} catch (Exception e) {
		    tx.rollback();
		}finally {
			tx.close();
		}
		
	}
	@Override
	public void save(List<User> users) {
		Transaction tx = getSession().beginTransaction();
		try{
			for (User user : users) {
				createOrUpdate(user);
			}
		    tx.commit();
		} catch (Exception e) {
		    tx.rollback();
		}finally {
			tx.close();
		}
		
	}

	@Override
	public List<User> find(Long supplierId, Long groupId, String code, String name, String email, String phone, int first, int pageSize) {
		long startTime = System.nanoTime();
		Map<String, Object> params = new HashMap<String,Object>();
		String MATCH ="MATCH (u:User)";
		String WHERE = " WHERE true";
		String RETURN =" RETURN u";
		String ORDER = "";
		String SKIP = "";
		String LIMIT = "";
		
		if(supplierId != null && supplierId > 0) {
			params.put("supplierId", supplierId);
			MATCH = "MATCH (s:Supplier) WHERE ID(s) = {supplierId}  WITH s MATCH (s)<-[r:REL_USER_SUPPLIER]-(u)";
			if(groupId != null && groupId >= 0) {
				params.put("groupId", groupId);
				MATCH += "-[:REL_USER_GROUP]->(g)";
				WHERE = " WHERE ID(g)= {groupId}";
			}
		}else if(groupId != null && groupId >= 0) {
			params.put("groupId", groupId);
			MATCH = "MATCH (g:Group) WHERE ID(g) = {groupId}  WITH g MATCH (g)<-[:REL_USER_GROUP]-(u)";
			if(supplierId != null && supplierId > 0) {
				params.put("supplierId", supplierId);
				MATCH += "-[r:REL_USER_SUPPLIER]->(s)";
				WHERE = " WHERE ID(s)= {supplierId}";
			}
		}
		if(StringUtils.isNoneBlank(code)) {
			params.put("code", code);
			WHERE += " AND u.code = {code}";
		}
		if(StringUtils.isNoneBlank(name)) {
			params.put("fullName", "(?ui).*"+name.trim()+".*");
			WHERE += " AND u.fullName =~ {fullName}";
		}
		if(StringUtils.isNoneBlank(email)) {
			params.put("email", "(?ui).*"+email.trim()+".*");
			WHERE += " AND u.email =~ {email}";
		}
		if(StringUtils.isNoneBlank(phone)) {
			params.put("phone", "(?ui).*"+phone.trim()+".*");
			WHERE += " AND u.phone =~ {phone}";
		}
		if(first >= 0) {
			params.put("first", first);
			SKIP = " SKIP {first}";
		}
		if(pageSize >= 0) {
			params.put("pageSize", pageSize);
			LIMIT = " LIMIT {pageSize}";
		}
		String cypher = MATCH + WHERE + RETURN + ORDER + SKIP + LIMIT;
//		System.out.println(cypher+"- params:"+params.toString());
		List<User> result = new ArrayList<User>();
		getSession().query(User.class,cypher, params).forEach(result::add);
		long totalTime = System.nanoTime() - startTime;
		logger.info("Cypher: "+cypher +" - params:"+params.toString() + " - TotalTime: "+totalTime + "(ns)");
        return result;
	}
	@Override
	public int count(Long supplierId, Long groupId, String code, String name, String email, String phone) {
		long startTime = System.nanoTime();
		Map<String, Object> params = new HashMap<String,Object>();
		String MATCH ="MATCH (u:User)";
		String WHERE = " WHERE true";
		String RETURN =" RETURN count(u)";
		String ORDER = "";
		if(supplierId != null && supplierId >= 0) {
			params.put("supplierId", supplierId);
			MATCH = "MATCH (s:Supplier) WHERE ID(s) = {supplierId}  WITH s MATCH (s)<-[r:REL_USER_SUPPLIER]-(u)";
			if(groupId != null && groupId > 0) {
				params.put("groupId", groupId);
				MATCH += "-[:REL_USER_GROUP]->(g)";
				WHERE = " WHERE ID(g)= {groupId}";
			}
		}else if(groupId != null && groupId >= 0) {
			params.put("groupId", groupId);
			MATCH = "MATCH (g:Group) WHERE ID(g) = {groupId}  WITH g MATCH (g)<-[:REL_USER_GROUP]-(u)";
			if(supplierId != null && supplierId > 0) {
				params.put("supplierId", supplierId);
				MATCH += "-[r:REL_USER_SUPPLIER]->(s)";
				WHERE = " WHERE ID(s)= {supplierId}";
			}
		}
		if(StringUtils.isNoneBlank(code)) {
			params.put("code", code);
			WHERE += " AND u.code = {code}";
		}
		if(StringUtils.isNoneBlank(name)) {
			params.put("fullName", "(?ui).*"+name.trim()+".*");
			WHERE += " AND u.fullName =~ {fullName}";
		}
		if(StringUtils.isNoneBlank(email)) {
			params.put("email", "(?ui).*"+email.trim()+".*");
			WHERE += " AND u.email =~ {email}";
		}
		if(StringUtils.isNoneBlank(phone)) {
			params.put("phone", "(?ui).*"+phone.trim()+".*");
			WHERE += " AND u.phone =~ {phone}";
		}
		String cypher = MATCH + WHERE + RETURN + ORDER;
//		System.out.println(cypher+"- params:"+params.toString());
		int result = getSession().queryForObject(Integer.class,cypher, params);
		long totalTime = System.nanoTime() - startTime;
		logger.info("Cypher: "+cypher +" - params:"+params.toString()+ " - TotalTime: "+totalTime + "(ns)");
        return result;
	}

}
