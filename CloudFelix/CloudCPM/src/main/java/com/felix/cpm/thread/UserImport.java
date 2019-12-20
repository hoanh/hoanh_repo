package com.felix.cpm.thread;

import java.util.List;

import com.felix.domain.User;
import com.felix.service.UserService;

public class UserImport implements Runnable{
	private UserService userService;
	private List<User> users;
	public UserImport() {
		// TODO Auto-generated constructor stub
	}
	
	public UserImport(UserService userService, List<User> users) {
		super();
		this.userService = userService;
		this.users = users;
	}

	@Override
	public void run() {
		userService.save(users);
		users.clear();
	}

}
