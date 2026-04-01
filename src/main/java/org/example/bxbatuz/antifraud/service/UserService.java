package org.example.bxbatuz.antifraud.service;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.AdminLinks;
import org.example.bxbatuz.antifraud.dto.LinkedUsersRes;
import org.example.bxbatuz.antifraud.dto.UsersList;
import org.example.bxbatuz.antifraud.entity.AdminDetails;
import org.example.bxbatuz.antifraud.entity.UserDetails;
import org.example.bxbatuz.antifraud.repo.AdminDetailsRepo;
import org.example.bxbatuz.antifraud.repo.LinkRepo;
import org.example.bxbatuz.antifraud.repo.LinkedUsersRepo;
import org.example.bxbatuz.antifraud.repo.UserDetailsRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final LinkRepo linkRepo;
    private final AdminService adminService;
    private final UserDetailsRepo userDetailsRepo;
    private final LinkedUsersRepo linkedUsersRepo;
    private final AdminDetailsRepo adminDetailsRepo;

    public ResponseEntity<List<UserDetails>> all(Boolean isFraud) {
        return isFraud == null
                ? ResponseEntity.ok(userDetailsRepo.findAll())
                : ResponseEntity.ok(userDetailsRepo.findByIsFraud(isFraud));
    }

    public ResponseEntity<UserDetails> byUserId(Long userId) {
        return ResponseEntity.ok(
                userDetailsRepo.findById(userId)
                        .orElseThrow(() -> new RuntimeException("user id not found!")));
    }

    public ResponseEntity<List<UsersList>> byLinkId(Long linkId) {
        return ResponseEntity.ok(linkedUsersRepo.findLinksAll(linkId));
    }

    public ResponseEntity<LinkedUsersRes> getLinkedUser(Long userId) {
        return ResponseEntity.ok(linkedUsersRepo.findUser(userId));
    }

    public ResponseEntity<List<UserDetails>> byAdminId(Boolean isFraud, Long adminId) {
        return ResponseEntity.ok(userDetailsRepo.findByAdminIdAndIsFraud(adminId, isFraud));
    }

    public ResponseEntity<List<AdminLinks>> allLinksAdminId(Long adminId) {
        AdminDetails admin = adminDetailsRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("admin id not found!"));
        if (admin.getRole().equals("super_admin"))
            return adminService.linkList();
        else
            return adminService.linksByAdminId(adminId);
    }
}
