package com.felix.domain;

import java.io.Serializable;


import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 
 * @author hoanh.hv
 */

@NodeEntity
public class Supplier extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6819947144731418643L;

	/**
	 * 
	 */
	

	/* private declare property */
    @Index(unique=true)
    private String code;

    private String name;
    
    
    
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

	
    
}