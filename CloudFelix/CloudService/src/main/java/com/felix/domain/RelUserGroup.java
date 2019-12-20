package com.felix.domain;

import java.io.Serializable;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;



@RelationshipEntity(type = "REL_USER_GROUP")
public class RelUserGroup  extends AbstractEntity implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1590196076209314276L;
	/**
	 * 
	 */
//	@Fetch
	@StartNode
	private User user;
	@EndNode
	private Group group;
	private boolean full;
	public RelUserGroup() {
	}
	public RelUserGroup(User user, Group group) {
		this.user = user;
		this.group = group;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isFull() {
		return full;
	}
	public void setFull(boolean full) {
		this.full = full;
	}
	
			
}
