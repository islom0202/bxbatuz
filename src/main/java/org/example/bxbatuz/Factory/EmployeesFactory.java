package org.example.bxbatuz.Factory;

import org.example.bxbatuz.Dto.AddEmployee;
import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Company;
import org.example.bxbatuz.Entity.Department;
import org.example.bxbatuz.Entity.Employees;
import org.example.bxbatuz.Enum.AuthRole;
import org.example.bxbatuz.Repo.AuthRepo;
import org.example.bxbatuz.Repo.CompanyRepo;
import org.example.bxbatuz.Repo.DepartmentRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

public class EmployeesFactory {
    public static Employees createEmployee(Auth auth, AddEmployee employee, Company company, Department department) {
        return new Employees(
                auth.getId(),
                employee.fullName(),
                employee.phone(),
                employee.position(),
                company,
                department,
                auth
        );
    }

}
