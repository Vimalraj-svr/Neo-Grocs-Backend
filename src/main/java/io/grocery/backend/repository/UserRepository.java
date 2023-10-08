package io.grocery.backend.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.grocery.backend.entity.User;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, String> {

 Optional<User> findByEmail(String email);
	
}
