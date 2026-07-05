package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.secondary.model.Users;
import com.app.secondary.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public List<Users> getAllUsersList() {
		
		try {
			List<Users> allUsersList = usersRepository.findAll();
			
			return allUsersList;
		} catch (Exception e) {
			throw e;
		}
	}

}
