package com.felix.service.iml;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import org.neo4j.ogm.session.Session;

import com.felix.domain.AbstractEntity;
import com.felix.service.Service;


public abstract class AbstractService<T extends AbstractEntity> implements Service<T> {

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
    public void delete(Long id) {
    	getSession().delete(getSession().load(getEntityType(), id));
    }

    @Override
    public T createOrUpdate(T entity) {
    	getSession().save(entity, DEPTH_ENTITY);
        return find(entity.getId());
    }
    @Override
    public T findByDepth(Long id, int depth) {
        return getSession().load(getEntityType(), id, depth);
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
