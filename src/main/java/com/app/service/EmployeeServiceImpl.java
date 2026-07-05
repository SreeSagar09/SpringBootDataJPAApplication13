package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.primary.model.Employee;
import com.app.primary.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployeesList() {
		
		try {
			List<Employee> employeesList = employeeRepository.findAll();
			
			return employeesList;
		} catch (Exception e) {
			throw e;
		}
	}

}
