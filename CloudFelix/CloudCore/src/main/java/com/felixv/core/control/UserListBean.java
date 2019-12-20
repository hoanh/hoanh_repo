package com.felixv.core.control;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.felix.domain.User;
import com.felix.service.UserService;


@Named
@ViewScoped
public class UserListBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4171697736290437401L;
	private List<User> users;
	private List<User> filteredUserList;
	private List<User> selectedUserList;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		loadUser();
	}
	private void loadUser() {
		users = new ArrayList<User>();
		userService.findAll().forEach(users::add);
	}

	public String getTotalRevenue() {
		return "0";
		
	}

	public void deleteUsers() {
		for (User user : selectedUserList) {
			this.userService.delete(user.getId());;

			if (filteredUserList != null) {
				this.filteredUserList.remove(user);
			}

			loadUser();
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getFilteredUserList() {
		return filteredUserList;
	}

	public void setFilteredUserList(List<User> filteredUserList) {
		this.filteredUserList = filteredUserList;
	}

	public List<User> getSelectedUserList() {
		return selectedUserList;
	}

	public void setSelectedUserList(List<User> selectedUserList) {
		this.selectedUserList = selectedUserList;
	}
}
