package io.grocery.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.grocery.backend.entity.Admin;
import io.grocery.backend.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	public boolean isAdmin(Admin adminToCheck)
	{
		  Admin adminInDb = adminRepository.findByEmail(adminToCheck.getEmail());

	        if (adminInDb != null && adminInDb.getPassword().equals(adminToCheck.getPassword())) {
	            return true; 
	        } else {
	            return false; 
	        }
	}
	
}
