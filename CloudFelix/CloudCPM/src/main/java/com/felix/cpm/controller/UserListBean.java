package com.felix.cpm.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.felix.domain.Group;
import com.felix.domain.Supplier;
import com.felix.domain.User;
import com.felix.service.GroupService;
import com.felix.service.SupplierService;
import com.felix.service.UserService;


@Named
@ViewScoped
public class UserListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4171697736290437401L;
	private LazyDataModel<User> users;
	private List<User> filteredUserList;
	private List<User> selectedUserList;
	private boolean searchAd;
	//Filter
	private List<Supplier> suppliers;
	private List<Group> groups;
	private Long supplierId;
	private Long groupId;
	private String code;
	private String name;
	private String email;
	private String phone;

	@Inject
	private UserService userService;
	@Inject
	private GroupService groupService;
	@Inject
	private SupplierService supplierService;

	@PostConstruct
	public void init() {
		System.out.println("UserListBean start...");
		searchAd = true;
		initModel();
		loadUser();
	}
	@PreDestroy
	public void finish() {
		System.out.println("UserListBean end.");
	}
	private void loadUser() {
//		users = new ArrayList<User>();
//		userService.findAll().forEach(users::add);
		groups = new ArrayList<Group>();
		groupService.findAll().forEach(groups::add);
		suppliers = new ArrayList<Supplier>();
		supplierService.findAll().forEach(suppliers::add);
	}

	public String getTotalRevenue() {
		return "0";
		
	}
	public void search() {
//		users = userService.find(supplierId,groupId,code,name,email,phone);
		searchAd = true;
	}
	public void initModel() {
		users = new LazyDataModel<User>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6141895619575338286L;

			@Override
			public List<User> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
				List<User> result = userService.find(supplierId, groupId, code, name, email, phone, first, pageSize);
				if(searchAd) {
					int totalRowCount = userService.count(supplierId, groupId, code, name, email, phone);
					this.setRowCount(totalRowCount);
					searchAd = false;
				}
				return result;
			}
        };
        
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

	public LazyDataModel<User> getUsers() {
		return users;
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
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isSearchAd() {
		return searchAd;
	}
	public void setSearchAd(boolean searchAd) {
		this.searchAd = searchAd;
	}
	
}
