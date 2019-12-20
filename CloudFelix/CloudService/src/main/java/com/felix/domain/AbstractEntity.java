/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.felix.domain;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 * 
 * @author hoanh.hv
 */
public abstract class AbstractEntity{

	/**
	 * 
	 */
//	private static final long serialVersionUID = 7066197807719422984L;
	/* protected declare property */
	@Id @GeneratedValue 
	protected Long id; 

	/* getter and setter method */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (id == null || obj == null || !getClass().equals(obj.getClass())) {
			return false;
		}
		return id.equals(((AbstractEntity) obj).id);

	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

}
