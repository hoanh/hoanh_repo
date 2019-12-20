package com.felix.service;

import org.neo4j.ogm.session.Session;

public interface Service<T> {

    Iterable<T> findAll();

    T find(Long id);
    
    T findOneBy(String key, Object value);
    
    T findByDepth(Long id, int depth);

    void delete(Long id);
    
    T createOrUpdate(T object);
    
    Session getSession();

}
