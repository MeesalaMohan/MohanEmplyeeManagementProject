package com.test.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.Dto.AuthRequest;
import com.test.Dto.EmpDto;
import com.test.Service.EmployeeService;
import com.test.Service.JwtService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/emp")
@Slf4j
public class EmpController {
	@Autowired
	public EmployeeService empservice;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

//
	@PostMapping("/save")
	public ResponseEntity<?> Employyeadd(@Validated @RequestBody EmpDto emp, BindingResult bindingresult) {
		if (bindingresult.hasErrors()) {
			Map<String, String> errorsmap = new HashMap<String, String>();
			for (FieldError error : bindingresult.getFieldErrors()) {
				errorsmap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(errorsmap, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<EmpDto>(empservice.add(emp), HttpStatus.OK);
		}
	}
//getting all employees

	@GetMapping("/allEmployees")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<EmpDto>> getallEmployee(
			@RequestParam(value = "Pageno", defaultValue = "0", required = false) Integer pageno,
			@RequestParam(value = "pagesize", defaultValue = "2", required = false) Integer pagesize,
			@RequestParam(value = "sotrby", defaultValue = "id", required = false) String sotrby,
			@RequestParam(value = "sotrdsc", defaultValue = "asc", required = false) String sotrdsc) {
		return new ResponseEntity<List<EmpDto>>(empservice.getallEmployee(pageno, pagesize, sotrby, sotrdsc),
				HttpStatus.OK);
	}
// delete by id

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteEmployeee(@PathVariable Integer id) {
		System.out.println(id);

		empservice.deleteEmployeee(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);

	}
// getting by id

	@GetMapping("/getbyid/{id}")

	@PreAuthorize("hasAuthority('ROLE_USER')")

	public ResponseEntity<EmpDto> getbyid(@PathVariable Integer id) {
		empservice.getbyid(id);

		return new ResponseEntity<EmpDto>(empservice.getbyid(id), HttpStatus.OK);
	}

// update employee
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PatchMapping("/patchemp/{id}")
	public ResponseEntity<EmpDto> updateemployee(@PathVariable int id, @RequestBody EmpDto emp) {
		return new ResponseEntity<EmpDto>(empservice.updateemployee(id, emp), HttpStatus.OK);

	}

	@PostMapping("/authintcate")
	public String createtoken(@RequestBody AuthRequest authRequest) {
		String auth = authRequest.getUserName();

		System.out.println("username for authrequest " + authRequest);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		if (authentication.isAuthenticated()) {
			log.info("entrym in controller 3 {}", authRequest.getUserName());
			return jwtService.generatetoken(authRequest.getUserName());
		} else {
			throw new UsernameNotFoundException("invalid user");
		}
	}

}
