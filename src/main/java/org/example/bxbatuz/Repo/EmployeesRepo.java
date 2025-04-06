package org.example.bxbatuz.Repo;

import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeesRepo extends JpaRepository<Employees, Long> {
    Optional<Employees> findByAuth(Auth auth);
}
