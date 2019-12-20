package com.felix.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 
 * @author hoanh.hv
 */

/**
 * @author OS
 *
 */
@NodeEntity
public class Group extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7272553541102620695L;

	/* private declare property */
    @Index
    private String code;

    private String name;
    @Relationship(type = "REL_GROUP_SUPPLIER")
    protected Supplier supplier;
   
    private String description;
   

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	
    
}