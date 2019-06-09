package com.beet.firstappdemo.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.beet.firstappdemo.domain.User;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */
@Repository
public class UserRepository {

    private final ConcurrentMap<Integer, User> repository = new ConcurrentHashMap<>();
    private final static AtomicInteger idGenerator = new AtomicInteger();

    
    /**
     * Save user object
     * @param user {@link User} object
     * @return if save successfully, return <code>true</code>,
     *         otherwise, return <code>false</code>
     */
    public boolean save(User user) {
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.put(id, user) == null;
    }
}