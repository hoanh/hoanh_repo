package com.felix.service;

import java.util.List;
import java.util.Map;


import com.felix.domain.Group;
import com.felix.domain.User;


public interface UserService extends Service<User>{
	Iterable<Map<String, Object>> getGroupByUser();

	void update(User user, List<Group> selectedGroups);

	void add(User user, List<Group> selectedGroups);
	
	void save(List<User> users);

	List<User> find(Long supplierId, Long groupId, String code, String name, String email, String phone, int first, int pageSize);
	int count(Long supplierId, Long groupId, String code, String name, String email, String phone);
}
