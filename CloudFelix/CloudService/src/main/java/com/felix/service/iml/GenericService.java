package com.felix.service.iml;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import org.neo4j.ogm.session.Session;

import com.felix.domain.TrackingEntity;
import com.felix.service.Service;


public abstract class GenericService<T extends TrackingEntity> implements Service<T> {

    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;
    private @EJB Neo4jSessionFactory neo4jSessionFactory;

    @Override
    public Iterable<T> findAll() {
        return getSession().loadAll(getEntityType(), DEPTH_LIST);
    }

    @Override
    public T find(Long id) {
        return getSession().load(getEntityType(), id, DEPTH_ENTITY);
    }
    
    @Override
    public T findByDepth(Long id, int depth) {
        return getSession().load(getEntityType(), id, depth);
    }

    @Override
    public void delete(Long id) {
    	getSession().delete(getSession().load(getEntityType(), id));
    }
    public void remove(T entity) {
		entity.setDeleted(Boolean.TRUE);
		entity.setLastModified(new Date());
		getSession().save(entity);
	}
    @Override
    public T createOrUpdate(T entity) {
    	if (entity.getId() == null) {
			entity.setCreatedDate(new Date());
		} else {
			entity.setLastModified(new Date());
		}
    	getSession().save(entity, DEPTH_ENTITY);
        return find(entity.getId());
    }
    @Override
    public T findOneBy(String key, Object value) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put(key, value);
    	return getSession().queryForObject(getEntityType(), "MATCH(n:"+getEntityType().getSimpleName()+") WHERE n."+key+"={"+key+"} RETURN n LIMIT 1",map);
    }

    abstract Class<T> getEntityType();
    @Override
    public Session getSession() {
    	return neo4jSessionFactory.getNeo4jSession();
    }
}
