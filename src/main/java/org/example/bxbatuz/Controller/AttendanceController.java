package org.example.bxbatuz.Controller;

import lombok.AllArgsConstructor;
import org.example.bxbatuz.Dto.CheckInRequest;
import org.example.bxbatuz.Dto.CheckOutRequest;
import org.example.bxbatuz.Response;
import org.example.bxbatuz.Service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@AllArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<Response> checkIn(@RequestBody CheckInRequest request){
        return ResponseEntity.ok(attendanceService.checkIn(request));
    }

    @PatchMapping("/check-out")
    public ResponseEntity<Response> checkOut(@RequestBody CheckOutRequest request){
        return ResponseEntity.ok(attendanceService.checkOut(request));
    }

}
