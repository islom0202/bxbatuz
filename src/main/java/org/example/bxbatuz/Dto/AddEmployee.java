package org.example.bxbatuz.Dto;

public record AddEmployee(
        Long companyId,
        Long departmentId,
        String fullName,
        String phone,
        String position,
        String email
) {
}
