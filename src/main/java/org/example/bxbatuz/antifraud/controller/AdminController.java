package org.example.bxbatuz.antifraud.controller;


import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.AdminLinks;
import org.example.bxbatuz.antifraud.dto.LoginReq;
import org.example.bxbatuz.antifraud.dto.SaveAdminReq;
import org.example.bxbatuz.antifraud.entity.AdminDetails;
import org.example.bxbatuz.antifraud.entity.Links;
import org.example.bxbatuz.antifraud.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(
            @RequestBody SaveAdminReq req
    ) {
        return adminService.createAdmin(req);
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable("adminId") Long adminId){
        return adminService.delete(adminId);
    }

    @PutMapping("/edit-status/{adminId}")
    public ResponseEntity<String> editAdmin(
            @PathVariable("adminId") Long adminId,
            @RequestParam(value = "makeActive") Boolean makeActive
    ){
        return adminService.makeActive(adminId, makeActive);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginReq req
    ){
        return adminService.login(req);
    }

    @PostMapping("/create-link/{adminId}")
    public ResponseEntity<Links> createLink(
            @PathVariable(value = "adminId") Long adminId,
            @RequestParam Long concursId
    ){
        return adminService.createLink(adminId, concursId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AdminDetails>> allAdmins(){
        return adminService.adminsList();
    }

    @GetMapping("/link-list")
    public ResponseEntity<List<AdminLinks>> linkList(){
        return adminService.linkList();
    }

    @GetMapping("/links/{adminId}")
    public ResponseEntity<List<AdminLinks>> getAllLinksByAdminId(
            @PathVariable(value = "adminId") Long adminId){
        return adminService.linksByAdminId(adminId);
    }

    @GetMapping("/profile/{adminId}")
    public ResponseEntity<AdminDetails> getAdminById(
            @PathVariable(value = "adminId") Long adminId
    ){
        return adminService.adminById(adminId);
    }

    @GetMapping("/active-links-num")
    public ResponseEntity<Integer> activeLinksNum(){
        return adminService.activeLinksNum();
    }
}
