package com.felix.service.iml;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.felix.domain.Group;
import com.felix.domain.Supplier;
import com.felix.service.SupplierService;
@Singleton
@Startup
public class InitBaseDataNeo4j {
	private @EJB Neo4jSessionFactory neo4jSessionFactory;
	private @EJB SupplierService supplierService;
    @PostConstruct
    public void init() {
    	Supplier sup = supplierService.findOneBy("code", "Felix");
    	if(sup == null)
    	initData();
    }
    private void initData() {
		//init Supplier
    	Transaction tx = getNeo4jSession().beginTransaction();
    	try {
    		getNeo4jSession().beginTransaction();
        	Supplier sup = new Supplier();
        	sup.setCode("Felix");
        	sup.setName("Công ty cổ phần công nghệ Felix");
        	Supplier sup1 = new Supplier();
        	sup1.setCode("Karofi");
        	sup1.setName("Công ty cổ phần Karofi Việt Nam");
        	//init Role
        	Group fgroup = new Group();
        	fgroup.setCode("FELIX_ADMIN");
        	fgroup.setName("FELIX ADMIN");
        	fgroup.setSupplier(sup);
        	Group sgroup = new Group();
        	sgroup.setCode("SUP_ADMIN");
        	sgroup.setName("SUP ADMIN");
        	sgroup.setSupplier(sup1);
        	getNeo4jSession().save(fgroup);
        	getNeo4jSession().save(sgroup);
    	    tx.commit();
    	} catch (Exception e) {
    	    tx.rollback();
    	}finally {
			tx.close();
		};
		
	}
	public Session getNeo4jSession() {
        return neo4jSessionFactory.getNeo4jSession();
    }
    
}
