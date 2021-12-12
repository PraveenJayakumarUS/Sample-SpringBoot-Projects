package com.enterprise.kanchi.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.kanchi.web.model.Employee;

// Design Patter Model-View-Controller

@RestController
public class EmployeeController {
	
	HashMap<Integer,Employee> employeeCache = new HashMap<>();

	@GetMapping(value = "/v1/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getEmployees(){
		
		List<Employee> employeeList = new ArrayList<>(employeeCache.values());
		
		return new ResponseEntity<>(employeeList,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
		
		if(employeeCache.containsKey(employee.getId())) {
			return new ResponseEntity<>("Employee already exists", HttpStatus.BAD_REQUEST);
		}
		
		employeeCache.put(employee.getId(), employee);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/v1/employee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,@PathVariable(value = "id") Integer id){
		
		if(!id.equals(employee.getId())) {
			return new ResponseEntity<>("You cannot update ID",HttpStatus.BAD_REQUEST);
		}
		
		//update only existing employee
		
		if(employeeCache.containsKey(id)) {
			
			employeeCache.put(employee.getId(),employee);
			
			return new ResponseEntity<>("Employee details updated successully", HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
	}
	@GetMapping(value = "/v1/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Integer id){
		if(employeeCache.containsKey(id)) {

			Employee employee =  employeeCache.get(id);

			return new ResponseEntity<>(employee,HttpStatus.OK);
			
		}
		

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
