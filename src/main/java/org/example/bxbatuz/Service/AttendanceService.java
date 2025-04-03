package org.example.bxbatuz.Service;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.Dto.CheckInRequest;
import org.example.bxbatuz.Dto.CheckOutRequest;
import org.example.bxbatuz.Entity.Attendance;
import org.example.bxbatuz.Entity.Employees;
import org.example.bxbatuz.Enum.AttendanceStatus;
import org.example.bxbatuz.Repo.AttendanceRepo;
import org.example.bxbatuz.Repo.EmployeesRepo;
import org.example.bxbatuz.Response;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepo attendanceRepo;
    private final EmployeesRepo employeesRepo;

    public Response checkIn(CheckInRequest request) {
        Employees employee = employeesRepo.findById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("employee not found"));

        AttendanceStatus status = getAttendanceStatus(employee, request.checkIn());

        Attendance attendance = new Attendance(
                employee,
                request.date(),
                request.note(),
                request.checkIn(),
                status
        );
        attendanceRepo.save(attendance);

        return new Response("saved", true);
    }

    private AttendanceStatus getAttendanceStatus(Employees employee, LocalTime checkIn) {
        LocalTime startTime = employee.getStartTime();

        long gracePeriodInMinutes = 5;

        if (checkIn.isBefore(startTime.minusMinutes(gracePeriodInMinutes))) {
            return AttendanceStatus.EARLY;
        } else if (checkIn.isAfter(startTime.plusMinutes(gracePeriodInMinutes))) {
            return AttendanceStatus.LATE;
        } else {
            return AttendanceStatus.PRESENT;
        }
    }

    public Response checkOut(CheckOutRequest request) {
        Attendance attendance = attendanceRepo
                .findByDateAndEmployees_Id(request.date(), request.userId())
                .orElseThrow(()->new NoSuchElementException("attendance data not found"));

        Double workedHours = calculateWorkedHours(attendance.getCheckIn(), request.checkOut());

        attendance.setCheckOut(request.checkOut());
        attendance.setWorkedHours(workedHours);
        attendance.setNotes(request.note());

        attendanceRepo.save(attendance);
        return new Response("updated", true);
    }

    private Double calculateWorkedHours(LocalTime checkIn, LocalTime checkOut) {
        Duration duration = Duration.between(checkIn, checkOut);
        long minutes = duration.toMinutes();
        return minutes / 60.0;
    }
}
