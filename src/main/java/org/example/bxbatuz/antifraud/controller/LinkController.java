package org.example.bxbatuz.antifraud.controller;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.AdminLinks;
import org.example.bxbatuz.antifraud.dto.UsersList;
import org.example.bxbatuz.antifraud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/link")
@RequiredArgsConstructor
public class LinkController {
    private final UserService userService;

    @GetMapping("/users/{linkId}")
    public ResponseEntity<List<UsersList>> getLinkedUsers(
            @PathVariable(value = "linkId") Long linkId){
        return userService.byLinkId(linkId);
    }

    @GetMapping("/list/{adminId}")
    public ResponseEntity<List<AdminLinks>> allLinksByAdminId(
            @PathVariable(value = "adminId") Long adminId){
        return userService.allLinksAdminId(adminId);
    }

    @PutMapping("/update-status/{linkId}")
    public ResponseEntity<String> updateStatus(
            @PathVariable("linkId") Long linkId,
            @RequestParam(value = "status") Boolean status){
        return userService.updateStatus(linkId, status);
    }

    @DeleteMapping("/delete/{linkId}")
    public ResponseEntity<String> deleteLink(
            @PathVariable("linkId") Long linkId){
        return userService.delete(linkId);
    }
}
