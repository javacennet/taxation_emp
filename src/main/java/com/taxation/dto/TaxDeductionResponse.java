package com.taxation.dto;

import lombok.Data;

@Data
public class TaxDeductionResponse {
	private String employeeCode;
	private String firstName;
	private String lastName;
	private double yearlySalary;
	private double taxAmount;
	private double cessAmount;

	public TaxDeductionResponse() {

	}

	public TaxDeductionResponse(String employeeCode, String firstName, String lastName, double yearlySalary,
			double taxAmount, double cessAmount) {
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.yearlySalary = yearlySalary;
		this.taxAmount = taxAmount;
		this.cessAmount = cessAmount;
	}

}
