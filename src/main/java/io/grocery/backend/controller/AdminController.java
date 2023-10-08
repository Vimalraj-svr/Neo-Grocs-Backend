package io.grocery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.grocery.backend.entity.Admin;
import io.grocery.backend.service.AdminService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/isadmin")
	public boolean isAdmin(@RequestBody Admin adminToCheck)
	{
		return adminService.isAdmin(adminToCheck);
	}
	
}
