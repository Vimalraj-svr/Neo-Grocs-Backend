package io.grocery.backend.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.grocery.backend.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

 Optional<User> findByEmail(String email);
 Optional<User> findByUid(Long uid);
	
}
