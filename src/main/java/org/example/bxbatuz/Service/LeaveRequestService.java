//package org.example.bxbatuz.Service;
//
//import lombok.RequiredArgsConstructor;
//import org.example.bxbatuz.Dto.LeaveRequestDto;
//import org.example.bxbatuz.Entity.Attendance;
//import org.example.bxbatuz.Entity.Employees;
//import org.example.bxbatuz.Entity.LeaveRequest;
//import org.example.bxbatuz.Repo.AttendanceRepo;
//import org.example.bxbatuz.Repo.EmployeesRepo;
//import org.example.bxbatuz.Repo.LeaveRequestRepo;
//import org.example.bxbatuz.Response;
//import org.springframework.stereotype.Service;
//
//import java.util.NoSuchElementException;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//@RequiredArgsConstructor
//public class LeaveRequestService {
//
//    private final LeaveRequestRepo leaveRequestRepo;
//    private final EmployeesRepo employeesRepo;
//    private final AttendanceRepo attendanceRepo;
//
//    public Response createLeaveRequest(LeaveRequestDto request) {
//        CompletableFuture<Employees> employeeFuture = CompletableFuture
//                .supplyAsync(() -> employeesRepo.findById(request.userId())
//                .orElseThrow(() -> new NoSuchElementException("employee not found"))
//        );
//
//        CompletableFuture<Attendance> attendanceFuture = CompletableFuture
//                .supplyAsync(()-> attendanceRepo)
//        LeaveRequest leaveRequest = new LeaveRequest(
//                employee,
//
//                );
//        return new Response("sent", true);
//    }
//}
