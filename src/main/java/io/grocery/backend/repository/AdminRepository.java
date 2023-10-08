package io.grocery.backend.repository;

import org.springframework.data.repository.CrudRepository;

import io.grocery.backend.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByEmail(String email);
}
