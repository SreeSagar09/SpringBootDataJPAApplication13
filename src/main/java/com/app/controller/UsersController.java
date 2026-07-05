package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.secondary.model.Users;
import com.app.service.UsersService;

@RestController
@RequestMapping(path = "/users")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@GetMapping(path = "/allUsers")
	public ResponseEntity<List<Users>> getAllUsers(){
		ResponseEntity<List<Users>> responseEntity = null;
		
		try {
			List<Users> allUsersList = usersService.getAllUsersList();
			
			responseEntity = new ResponseEntity<List<Users>>(allUsersList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
