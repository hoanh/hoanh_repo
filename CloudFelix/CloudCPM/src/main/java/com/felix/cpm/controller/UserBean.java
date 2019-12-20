package com.felix.cpm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.felix.cpm.thread.UserImport;
import com.felix.cpm.web.util.Util;
import com.felix.domain.Group;
import com.felix.domain.RelUserGroup;
import com.felix.domain.User;
import com.felix.service.GroupService;
import com.felix.service.SupplierService;
import com.felix.service.UserService;
import javax.faces.view.ViewScoped;



@Named
@ViewScoped
public class UserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498074863597561797L;

	@NotEmpty
	private String code;

	private List<Group> selectedGroups;

	@NotEmpty
	private String password;
	private boolean locked;
	private String email;
	private String name;
	private String phone;
	private Long id;
	@NotNull
	@PositiveOrZero
	private Integer gender;
	@NotNull
	@PositiveOrZero
	private Integer type;

	@NotNull
	private Date birthday;

	private List<Group> availableGroups;

	@Inject
	private GroupService groupService;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		System.out.println("UserBean start...");
		setId(Util.getRequestLongParam("id"));
		if(getId() != null) {
			filData();
		}
		availableGroups = new ArrayList<Group>();
		groupService.findAll().forEach(availableGroups::add);
	}
	@PreDestroy
	public void finish() {
		System.out.println("UserBean end.");
	}
	private void filData() {
		User user = userService.find(id);
		getSelectedGroups().clear();
		if(user != null) {
			setCode(user.getCode());
			setPassword(user.getPassword());
			setName(user.getFullName());
			setBirthday(user.getBirthday());
			setEmail(user.getEmail());
			setGender(user.getGender());
			setPhone(user.getPhone());
			setType(user.getType());
			if(user.getRels() != null)
			for (RelUserGroup group : user.getRels()) {
				System.out.println("GroupCode: "+group.getGroup().getCode());
				selectedGroups.add(group.getGroup());
			}
//			setSelectedGroups(groupService.findByUser(id));
		}
	}

	public String createUser() {
		if(userService.findOneBy("code", code) != null) {
			Util.show("Tên đang nhập đã được sử dụng", FacesMessage.SEVERITY_WARN);
			return "";
		};
		User user = new User(code, password, name, email, birthday,
				gender, phone,type, "");
		userService.add(user,selectedGroups);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Successful", "Sucessfully created user:  " + user.getCode()));
		context.getExternalContext().getFlash().setKeepMessages(true);

		return "/user.xhtml?faces-redirect=true";
	}
	public String updateUser() {
		User user = userService.find(id);
		User old = userService.findOneBy("code", code);
		if(old != null && !old.getId().equals(id)) {
			Util.show("Tên đang nhập đã được sử dụng", FacesMessage.SEVERITY_WARN);
			return "";
		};
		user.setCode(code);
		user.setPassword(password);
		user.setFullName(name);
		user.setEmail(email);
		user.setBirthday(birthday);
		user.setGender(gender);
		user.setPhone(phone);
		user.setType(type);
		userService.update(user,selectedGroups);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Successful", "Sucessfully updated user:  " + user.getCode()));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/user.xhtml?faces-redirect=true&id="+id;
	}
	public void generateUser() {
		Collection<Group> gus = groupService.getSession().loadAll(Group.class, 1);
		List<User> users = new ArrayList<User>();
		Long current = System.nanoTime();
		for (int i = 0; i < 50000; i++) {
			User user = new User();
			user.setCode("code_"+(current+i));
			user.setPassword("password_"+(current+i));
			user.setFullName("name_"+(current+i));
			user.setEmail("email_"+(current+i));
			user.setBirthday(new Date());
			user.setGender(new Random().nextInt(2));
			user.setPhone("phone_"+(current+i));
			user.setType(new Random().nextInt(3));
			if(gus != null && gus.size()>0) {
				Group group = (Group)gus.toArray()[new Random().nextInt(gus.size())];
				Set<RelUserGroup> groups = new HashSet<RelUserGroup>();
				RelUserGroup rel = new RelUserGroup(user, group);
				groups.add(rel);
			    user.setRels(groups);
			    user.setSupplier(group.getSupplier());
			}
			users.add(user);
			if(users.size() == 5000) {
				new Thread(new UserImport(userService,users)).start();
				users = new ArrayList<User>();
			}
			
		}
		
		userService.save(users);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Successful", "Sucessfully generate user:  " + users.size()));
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	public List<Group> getAvailableGroups() {
		return availableGroups;
	}

	

	public List<Group> getSelectedGroups() {
		if(selectedGroups == null) {
			selectedGroups = new ArrayList<Group>();
		}
		return selectedGroups;
	}

	public void setSelectedGroups(List<Group> selectedGroups) {
		this.selectedGroups = selectedGroups;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	


}
