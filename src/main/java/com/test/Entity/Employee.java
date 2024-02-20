package com.test.Entity;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EmployyeDetials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	// @Column(nullable = false)
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private double salary;
	@Column(nullable = false)
	private String doj;
	@Column(nullable = false)
	private String phonenumber;

}
