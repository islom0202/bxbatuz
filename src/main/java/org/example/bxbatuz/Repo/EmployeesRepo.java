package org.example.bxbatuz.Repo;

import org.example.bxbatuz.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepo extends JpaRepository<Employees, Long> {
}
