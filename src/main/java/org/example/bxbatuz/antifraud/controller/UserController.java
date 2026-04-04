package org.example.bxbatuz.antifraud.controller;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.LinkedUsersRes;
import org.example.bxbatuz.antifraud.dto.UserTotals;
import org.example.bxbatuz.antifraud.entity.UserDetails;
import org.example.bxbatuz.antifraud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDetails>> getAllUsers(
            @RequestParam Boolean isFraud
    ) {
        return userService.all(isFraud);
    }

    @GetMapping("/list/{adminId}")
    public ResponseEntity<List<UserDetails>> getAllUsersByAdminId(
            @PathVariable("adminId") Long adminId,
            @RequestParam Boolean isFraud
    ) {
        return userService.byAdminId(isFraud, adminId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetails> getUserDetails(
            @PathVariable(value = "userId") Long userId) {
        return userService.byUserId(userId);
    }

    @GetMapping("/linked-user/{userId}")
    public ResponseEntity<LinkedUsersRes> getLinkedUser(
            @PathVariable(value = "userId") Long userId) {
        return userService.getLinkedUser(userId);
    }

    @GetMapping("/totals/{adminId}")
    public ResponseEntity<UserTotals> getAllUsersTotals(
            @PathVariable("adminId") Long adminId) {
        return userService.totals(adminId);
    }
}
