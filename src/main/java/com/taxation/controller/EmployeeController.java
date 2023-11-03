package com.taxation.controller;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taxation.dto.TaxDeductionResponse;
import com.taxation.entity.Employee;
import com.taxation.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping("/store")
	public ResponseEntity<String> storeEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return ResponseEntity.ok("Employee details stored successfully");
	}
	
	@GetMapping("/tax-deduction/{employeeId}")
	public ResponseEntity<TaxDeductionResponse> calculateTaxDeduction(@PathVariable String employeeId) {
	    Employee employee = employeeRepository.findByEmployeeId(employeeId);

	    if (employee == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }

	    // Calculate the tax deduction for the employee based on the rules and tax slabs
	    double yearlySalary = calculateYearlySalary(employee);
	    double taxAmount = calculateTaxAmount(yearlySalary);
	    double cessAmount = calculateCessAmount(yearlySalary);

	    TaxDeductionResponse response = new TaxDeductionResponse();
	    response.setEmployeeCode(employee.getEmployeeId());
	    response.setFirstName(employee.getFirstName());
	    response.setLastName(employee.getLastName());
	    response.setYearlySalary(yearlySalary);
	    response.setTaxAmount(taxAmount);
	    response.setCessAmount(cessAmount);

	    return ResponseEntity.ok(response);
	}
	
	private double calculateYearlySalary(Employee employee) {
	    // Calculate yearly salary considering the DOJ
	    LocalDate currentDate = LocalDate.now();
	    LocalDate doj = employee.getDoj();

	    int monthsWorked = Period.between(doj, currentDate).getMonths();
	    double yearlySalary = employee.getMonthlySalary() * monthsWorked / 12;

	    return yearlySalary;
	}

	private double calculateTaxAmount(double yearlySalary) {
	    double taxAmount = 0;

	    if (yearlySalary <= 250000) {
	        // No tax
	        taxAmount = 0;
	    } else if (yearlySalary <= 500000) {
	        // 5% Tax for the portion above 250000
	        taxAmount = (yearlySalary - 250000) * 0.05;
	    } else if (yearlySalary <= 1000000) {
	        // 5% Tax on the first 250000, 10% on the portion above 250000 up to 500000
	        taxAmount = (250000 * 0.05) + ((yearlySalary - 500000) * 0.1);
	    } else {
	        // 5% Tax on the first 250000, 10% on the portion above 250000 up to 500000, 20% on the rest
	        taxAmount = (250000 * 0.05) + (500000 * 0.1) + ((yearlySalary - 1000000) * 0.2);
	    }

	    return taxAmount;
	}

	private double calculateCessAmount(double yearlySalary) {
	    double cessAmount = 0;

	    if (yearlySalary > 2500000) {
	        // Collect 2% cess on the amount above 2500000
	        cessAmount = (yearlySalary - 2500000) * 0.02;
	    }
	    return cessAmount;
	}

}
