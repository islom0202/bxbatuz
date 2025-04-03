package org.example.bxbatuz.Repo;

import org.example.bxbatuz.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByDate(LocalDate date);
    Optional<Attendance> findByDateAndEmployees_Id(LocalDate date, Long employeeId);
}
