package org.example.bxbatuz.Factory;

import org.example.bxbatuz.Dto.AddEmployee;
import org.example.bxbatuz.Entity.Auth;
import org.example.bxbatuz.Entity.Company;
import org.example.bxbatuz.Entity.Department;
import org.example.bxbatuz.Entity.Employees;

import java.time.Duration;
import java.time.LocalTime;

public class EmployeesFactory {
    public static Employees createEmployee(Auth auth,
                                           AddEmployee employee,
                                           Company company,
                                           Department department) {

        Double officialWorkHours = calculateWorkHours(employee.startTime(), employee.endTime());
        return new Employees(
                auth.getId(),
                employee.fullName(),
                employee.phone(),
                employee.position(),
                officialWorkHours,
                employee.startTime(),
                employee.endTime(),
                company,
                department,
                auth
        );
    }

    private static Double calculateWorkHours(LocalTime startTime, LocalTime endTime) {
        if (endTime.isBefore(startTime)) {
            endTime = endTime.plusHours(24);
        }
        Duration duration = Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        return minutes / 60.0;
    }

}
