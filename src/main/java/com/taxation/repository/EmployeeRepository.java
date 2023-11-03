package com.taxation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxation.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	Employee findByEmployeeId(String employeeId);

}
