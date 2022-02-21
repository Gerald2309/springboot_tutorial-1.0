package com.javatechnolessons.demo.controller;
import java.util.*;

import com.javatechnolessons.demo.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.javatechnolessons.demo.repository.IEmployeeJpaRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api")

public class EmployeeController {
    @Autowired
	IEmployeeJpaRepository IEmployeeJpaRepository;

    @GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String firstName) {
		try {
			List<Employee> Employees = new ArrayList<Employee>();

			if (firstName == null)
				IEmployeeJpaRepository.findAll().forEach(Employees::add);
			else
				IEmployeeJpaRepository.findByFirstName(firstName).forEach(Employees::add);

			if (Employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/Employee/{id}")  //Buscar empleado por id
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
		Optional<Employee> EmployeeData = IEmployeeJpaRepository.findByEmployeeid(id);

		if (EmployeeData.isPresent()) {
			return new ResponseEntity<>(EmployeeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
