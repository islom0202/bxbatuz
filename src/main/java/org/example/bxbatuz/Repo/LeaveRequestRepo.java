package org.example.bxbatuz.Repo;

import org.example.bxbatuz.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {
}
