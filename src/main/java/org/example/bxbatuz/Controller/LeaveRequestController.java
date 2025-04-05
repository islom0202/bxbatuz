//package org.example.bxbatuz.Controller;
//
//import lombok.AllArgsConstructor;
//import org.example.bxbatuz.Dto.LeaveRequestDto;
//import org.example.bxbatuz.Response;
//import org.example.bxbatuz.Service.LeaveRequestService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/leave-request")
//@AllArgsConstructor
//public class LeaveRequestController {
//
//    private final LeaveRequestService leaveRequestService;
//
//    @PostMapping("/create")
//    public ResponseEntity<Response> createLeaveRequest(@RequestBody LeaveRequestDto request){
//        return ResponseEntity.ok(leaveRequestService.createLeaveRequest(request));
//    }
//}
