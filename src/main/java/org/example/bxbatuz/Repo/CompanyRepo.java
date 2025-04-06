package org.example.bxbatuz.Repo;

import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Optional<Company> findByAuth(Auth auth);
}
