package com.taxation.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Employee ID is mandatory")
    private String employeeId;
    
    @NotBlank(message = "First Name is mandatory")
    private String firstName;
    
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @ElementCollection
    private List<@Pattern(regexp = "\\d{10}", message = "Invalid phone number format") String> phoneNumbers;
   
    @NotNull(message = "Date of Joining is mandatory")
    private LocalDate doj;
    
    @Positive(message = "Monthly Salary must be a positive number")
    private double monthlySalary;

}
