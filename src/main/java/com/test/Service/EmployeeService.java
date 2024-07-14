package com.test.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.test.Dto.EmpDto;
import com.test.Entity.Employee;
import com.test.Exception.MyEmployeeException;
import com.test.Repositry.Repositry;

@Service

public class EmployeeService {
	@Autowired
	private Repositry repo;
	@Autowired
	private ModelMapper mapper;

//save the employee
	public EmpDto add(EmpDto empdto) {
		// TODO Auto-generated method stub
		Employee returnemp = null;
		Employee emp = mapper.map(empdto, Employee.class);
		try {
			returnemp = repo.save(emp);
		} catch (Exception e) {

			throw new MyEmployeeException("user already exist");

		}

		EmpDto reempdto = mapper.map(returnemp, EmpDto.class);
		return reempdto;
	}

// get all employees 
	public List<EmpDto> getallEmployee(int pageno, int psgesize, String sortby, String sortdrc) {
		// TODO Auto-generated method stub
		Sort sort = sortdrc.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortby).ascending()
				: Sort.by(sortby).descending();

		Pageable pag = PageRequest.of(pageno, psgesize, sort);
		Page<Employee> Emplist = repo.findAll(pag);
		ArrayList<EmpDto> empdtolist = (ArrayList<EmpDto>) Emplist.stream()
				.map((Employee) -> mapper.map(Employee, EmpDto.class)).collect(Collectors.toList());
		return empdtolist;
	}
	// delete by id

	public void deleteEmployeee(int id) {
		// TODO Auto-generated method stub
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			throw new MyEmployeeException("does not have user in  your id");
		}
	}

// get by id method
	public EmpDto getbyid(Integer id) throws NullPointerException {
		// TODO Auto-generated method stub
		EmpDto edto = null;

		try {
			Employee emp = repo.findById(id).get();

			edto = mapper.map(emp, EmpDto.class);

		} catch (Exception e) {
			throw new MyEmployeeException("there is no uesr with your id");
		}
		return edto;

	}

	// this use to update the employee detials

	public EmpDto updateemployee(int id, EmpDto emp) {
		Employee employee = repo.findById(id)
				.orElseThrow(() -> new MyEmployeeException("there is no uesr with your id"));
		if (emp.getFirstname() != null) {
			employee.setFirstname(emp.getFirstname());
		}
		if (emp.getLastname() != null) {
			employee.setLastname(emp.getLastname());
		}
		if (emp.getEmail() != null) {
			employee.setEmail(emp.getEmail());
		}
		if (emp.getPhonenumber() != null) {
			employee.setPhonenumber(emp.getPhonenumber());

		}
		if (emp.getSalary() != 0.0) {
			employee.setSalary(emp.getSalary());
		}
		if (emp.getDoj() != null) {
			employee.setDoj(emp.getDoj());
		}
		Employee employe = repo.save(employee);
		return mapper.map(employe, EmpDto.class);

	}
}
