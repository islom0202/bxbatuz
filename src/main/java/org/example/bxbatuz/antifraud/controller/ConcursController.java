package org.example.bxbatuz.antifraud.controller;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.ConcursName;
import org.example.bxbatuz.antifraud.dto.ConcursRes;
import org.example.bxbatuz.antifraud.dto.CreateConcursReq;
import org.example.bxbatuz.antifraud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/concurs")
@RequiredArgsConstructor
public class ConcursController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createConcurs(
            @RequestBody CreateConcursReq req
    ) {
        return userService.createConcurs(req);
    }

    @GetMapping("/list/{adminId}")
    public ResponseEntity<List<ConcursRes>> getList(
            @PathVariable("adminId") Long adminId
    ) {
        return userService.allConcurs(adminId);
    }

    @PutMapping("/update-status/{concursId}")
    public ResponseEntity<String> updateStatus(
            @PathVariable("concursId") Long concursId,
            @RequestParam("status") Boolean status){
        return userService.updateConcursStatus(concursId, status);
    }

    @DeleteMapping("/delete/{concursId}")
    public ResponseEntity<String> deleteConcurs(
            @PathVariable("concursId") Long concursId){
        return userService.deleteConcurs(concursId);
    }

    @GetMapping("/names/{adminId}")
    public ResponseEntity<List<ConcursName>> getAllNames(
            @PathVariable("adminId") Long adminId){
        return userService.concursName(adminId);
    }
}
