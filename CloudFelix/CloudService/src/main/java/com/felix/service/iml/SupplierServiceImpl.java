package com.felix.service.iml;


import javax.ejb.Singleton;

import com.felix.domain.Supplier;
import com.felix.service.SupplierService;

@Singleton
public class SupplierServiceImpl extends AbstractService<Supplier> implements SupplierService{

	
	@Override
	Class<Supplier> getEntityType() {
		return Supplier.class;
	}


}
