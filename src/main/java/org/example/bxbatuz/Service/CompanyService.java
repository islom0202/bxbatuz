package org.example.bxbatuz.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.Dto.AddEmployee;
import org.example.bxbatuz.Dto.SignupRequest;
import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Company;
import org.example.bxbatuz.Entity.Department;
import org.example.bxbatuz.Entity.Employees;
import org.example.bxbatuz.Enum.AuthRole;
import org.example.bxbatuz.Factory.EmployeesFactory;
import org.example.bxbatuz.PasswordGenerator;
import org.example.bxbatuz.Repo.AuthRepo;
import org.example.bxbatuz.Repo.CompanyRepo;
import org.example.bxbatuz.Repo.DepartmentRepo;
import org.example.bxbatuz.Repo.EmployeesRepo;
import org.example.bxbatuz.Response;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final AuthRepo authRepo;
    private final DepartmentRepo departmentRepo;
    private final EmployeesRepo employeesRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Transactional
    public Response save(SignupRequest request) {
        Auth auth = authRepo.save(Auth.createAuth(request.email(),
                passwordEncoder.encode(request.password()),
                AuthRole.COMPANY));

        Company company = new Company(auth.getId(),
                request.companyName(),
                request.empCount(),
                request.address(),
                auth);

        companyRepo.save(company);

        return new Response("Company saved successfully", true);
    }

    @Transactional
    public Response addEmployee(AddEmployee request) {
        String password = PasswordGenerator.generatePassword();

        CompletableFuture<Auth> authFuture = CompletableFuture
                .supplyAsync(() -> authRepo.save(
                        Auth.createAuth(request.email(),
                                passwordEncoder.encode(password),
                                AuthRole.USER)));

        CompletableFuture<Company> companyFuture = CompletableFuture
                .supplyAsync(() -> companyRepo.findById(request.companyId())
                .orElseThrow(() -> new NoSuchElementException("Company not found")));

        CompletableFuture<Department> departmentFuture = CompletableFuture
                .supplyAsync(() -> departmentRepo.findById(request.departmentId())
                .orElseThrow(() -> new NoSuchElementException("Department not found")));

        CompletableFuture.allOf(authFuture, companyFuture, departmentFuture).join();

        Auth auth = authFuture.join();
        Company company = companyFuture.join();
        Department department = departmentFuture.join();

        Employees employee = EmployeesFactory.createEmployee(auth, request, company, department);

        employeesRepo.save(employee);

        //todo: if any exception is happened,
        // use event listener to trigger email sending when db transaction is end
        // or use TransactionSynchronizationManager with afterCommit()

        CompletableFuture.runAsync(() ->
                emailService.sendEmailToEmployee(request.email(), password));

        return new Response("saved", true);
    }

}
