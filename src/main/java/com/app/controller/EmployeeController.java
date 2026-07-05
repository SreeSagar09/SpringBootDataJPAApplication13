package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.primary.model.Employee;
import com.app.service.EmployeeService;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(path = "/allEmployees")
	public ResponseEntity<List<Employee>> getAllEmployeesList(){
		ResponseEntity<List<Employee>> responseEntity = null;
		
		try {
			List<Employee> allEmployeeList = employeeService.getAllEmployeesList();
			
			responseEntity = new ResponseEntity<List<Employee>>(allEmployeeList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
