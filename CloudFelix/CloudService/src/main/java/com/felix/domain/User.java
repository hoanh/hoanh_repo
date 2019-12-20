package com.felix.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;



/**
 * 
 * @author hoanh.hv
 */

@NodeEntity
public class User extends TrackingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818954561305465094L;

	/**
	 * 
	 */
	/* private declare property */
	@Index
	private String code;
	private String password;
	private String fullName;
	private String email;
	@DateLong
	private Date birthday;
	
	private Integer gender; //0:Nữ, 1: Nam
	@Index
	private String phone;
	
	private int registrationBy; //0:App, 1 Admin
	
	private boolean system;

	private boolean locked;
	
	private int type; //0 admin, 1 cskh, 2 sale, 3 support, 4 staff
	
	public boolean isAdmin() {
		return type == 0;
	}

	public User() {
	}
	

	public User(String code, String password, String fullName, String email, Date birthday, Integer gender,
			String phone, int type, String urlAvatar) {
		super();
		this.code = code;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.type = type;
		this.urlAvatar = urlAvatar;
	}

	@Relationship(type = "REL_USER_SUPPLIER")
    protected Supplier supplier;
	private String urlAvatar;
	@Relationship(type = "REL_USER_GROUP")
	private Set<RelUserGroup> rels;

	/* getter and setter method */

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public int getRegistrationBy() {
		return registrationBy;
	}

	public void setRegistrationBy(int registrationBy) {
		this.registrationBy = registrationBy;
	}

	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Set<RelUserGroup> getRels() {
		return rels;
	}

	public void setRels(Set<RelUserGroup> rels) {
		this.rels = rels;
	}

	
	
}