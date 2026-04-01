package org.example.bxbatuz.antifraud.repo;

import org.example.bxbatuz.antifraud.entity.AdminDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDetailsRepo extends JpaRepository<AdminDetails, Long> {
    AdminDetails findByUsername(String username);
}
