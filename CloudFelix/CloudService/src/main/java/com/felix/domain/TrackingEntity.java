/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.felix.domain;

import java.util.Date;

import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

/**
 *
 * @author hoanh.hv
 */
public abstract class TrackingEntity extends AbstractEntity{

    /* getter and setter method */
	/* private declare property */

    /**
	 * 
	 */
//	private static final long serialVersionUID = 4373418389020839344L;

	@DateLong
    protected Date createdDate;

	@DateLong
    protected Date lastModified;
    
    protected boolean deleted;
    
    @DateLong
    protected Date deletedDate;
    
    @Relationship(type = "CREATED_BY")
    protected User createdBy;
    @Relationship(type = "DELETED_BY")
    protected User deletedBy;
    
    @Relationship(type = "MODIFIED_BY")
    protected User modifiedBy;


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public User getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
   
    
    
}
