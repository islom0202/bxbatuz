package org.example.bxbatuz.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileResponse implements ProfileResponse{
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private Double officialWorkHours;
    private LocalTime startTime;
    private LocalTime endTime;
    private String company;
    private String department;
}
