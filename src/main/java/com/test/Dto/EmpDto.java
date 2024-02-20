package com.test.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpDto {
	private int id;
	@NotBlank(message = "first name is notempty")

	private String firstname;
	@NotBlank(message = "first name is notempty")
	@NotNull(message = "lastname is notnull")
	private String lastname;
	@NotBlank(message = "first name is notempty")
	@NotNull(message = "email is notnull")
	@Email(message ="you enter certian emil format",regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	@Min(message="minimum number given", value = 1)
	@NotNull(message = "salary is notnull")
	private double salary;
	@NotBlank(message = "first name is notempty")
	@NotNull(message = "doj is notnull")
    @Pattern(regexp="^\\d{2}/\\d{2}/\\d{4}$",message="give format like doj")
	private String doj;
	@NotNull(message = "phonenumber is notnull")
	
	@Pattern(message="give me phone number format",regexp="^[6-9]\\d{9}$")
	private String  phonenumber;

}
