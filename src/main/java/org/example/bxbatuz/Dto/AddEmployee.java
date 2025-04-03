package org.example.bxbatuz.Dto;

import java.time.LocalTime;

public record AddEmployee(
        Long companyId,
        Long departmentId,
        String fullName,
        String phone,
        String position,
        LocalTime startTime,
        LocalTime endTime,
        String email
) {
}
