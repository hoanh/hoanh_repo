package com.felix.service;

import java.util.List;
import java.util.Map;

import com.felix.domain.Group;
import com.felix.domain.RelUserGroup;


public interface GroupService extends Service<Group>{
	Iterable<Map<String, Object>> getGroupByUser();
	List<Group> findByUser(Long id);
	List<RelUserGroup> findRelByUser(Long userId);
}
