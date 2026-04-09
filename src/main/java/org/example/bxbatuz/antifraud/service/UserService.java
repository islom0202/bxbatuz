package org.example.bxbatuz.antifraud.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.*;
import org.example.bxbatuz.antifraud.entity.*;
import org.example.bxbatuz.antifraud.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final LinkRepo linkRepo;
    private final ConcursRepo concursRepo;
    private final AdminService adminService;
    private final UserDetailsRepo userDetailsRepo;
    private final LinkedUsersRepo linkedUsersRepo;
    private final AdminDetailsRepo adminDetailsRepo;

    @Transactional
    public void logUserAndLink(
            LinkedUsers userDetail,
            FormReq dto,
            String ipAddress,
            LocationStats ipStats,
            Links link, LinkedUsers linkedUser) {
        Long userId;
        if (userDetail == null) {
            UserDetails user = new UserDetails();
            user.setUserPhone(dto.getPhone());
            user.setUserIp(ipAddress);
            user.setLatitude(dto.getLatitude());
            user.setLongitude(dto.getLongitude());
            user.setIpLatitude(ipStats.getLatitude());
            user.setIpLongitude(ipStats.getLongitude());
            user.setUserDeviceId(dto.getDeviceId());
            user.setIsFraud(false);
            user.setAdminId(link.getAdminId());
            user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            user = userDetailsRepo.save(user);
            userId = user.getId();
        } else userId = userDetail.getId();

        // 4. Link User to the Admin's Link
        LinkedUsers linkRelation = new LinkedUsers();
        linkRelation.setLinkId(link.getId());
        linkRelation.setUserId(userId);
        linkRelation.setUserCode(dto.getCode());
        linkRelation.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        linkRelation.setClickedAt(Timestamp.valueOf(LocalDateTime.now()));
        linkRelation.setUserCode(dto.getCode());
        linkRelation.setIsFraud(false);
        linkRelation.setUserPhone(dto.getPhone());
        linkRelation.setUserDeviceId(dto.getDeviceId());
        linkRelation.setConcursId(link.getConcursId());
        linkRelation.setLatitude(dto.getLatitude());
        linkRelation.setLongitude(dto.getLongitude());
        linkRelation.setIpLatitude(ipStats.getLatitude());
        linkRelation.setIpLongitude(ipStats.getLongitude());
        linkedUsersRepo.save(linkRelation);
    }

    public ResponseEntity<Page<UserDetails>> all(Boolean isFraud, int page) {
        Pageable pageable = PageRequest.of(page, 25, Sort.by(Sort.Direction.DESC, "createdAt"));
        return isFraud == null
                ? ResponseEntity.ok(userDetailsRepo.findAll(pageable))
                : ResponseEntity.ok(userDetailsRepo.findByIsFraud(isFraud, pageable));
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

    public ResponseEntity<Page<UserDetails>> byAdminId(Boolean isFraud, Long adminId, int page) {
        Pageable pageable = PageRequest.of(page, 25, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(userDetailsRepo.findByAdminIdAndIsFraud(adminId, isFraud, pageable));
    }

    public ResponseEntity<List<AdminLinks>> allLinksAdminId(Long adminId) {
        AdminDetails admin = adminDetailsRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("admin id not found!"));
        if (admin.getRole().equals("super_admin"))
            return adminService.linkList();
        else
            return adminService.linksByAdminId(adminId);
    }

    public ResponseEntity<String> updateStatus(Long linkId, Boolean status) {
        Links link = linkRepo.findById(linkId).orElseThrow(() -> new RuntimeException("Havola topilmadi!"));
        link.setIsExpired(status);
        linkRepo.save(link);
        return ResponseEntity.ok("Yangilandi!");
    }

    public ResponseEntity<String> delete(Long linkId) {
        linkRepo.deleteById(linkId);
        return ResponseEntity.ok("O`chirildi!");
    }

    public ResponseEntity<String> createConcurs(CreateConcursReq req) {
        concursRepo.save(Concurs.builder()
                .name(req.name())
                .description(req.description())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .isActive(true)
                .adminId(req.adminId())
                .build());
        return ResponseEntity.ok("Yaratildi!");
    }

    public ResponseEntity<List<ConcursRes>> allConcurs(Long adminId) {
        AdminDetails adminDetails = adminDetailsRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("admin topilmadi!"));
        if (adminDetails.getRole().equals("super_admin"))
            return ResponseEntity.ok(concursRepo.findAllList());
        else
            return ResponseEntity.ok(concursRepo.findAllByNativeSql(adminId));
    }

    public ResponseEntity<String> updateConcursStatus(Long concursId, Boolean status) {
        Concurs concurs = concursRepo.findById(concursId).orElseThrow(() -> new RuntimeException("concurs topilmadi!"));
        concurs.setIsActive(status);
        concursRepo.save(concurs);
        return ResponseEntity.ok("Yangilandi!");
    }

    public ResponseEntity<String> deleteConcurs(Long concursId) {
        concursRepo.deleteById(concursId);
        return ResponseEntity.ok("O`chirildi!");
    }

    public ResponseEntity<List<ConcursName>> concursName(Long adminId) {
        AdminDetails adminDetails = adminDetailsRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("admin topilmadi!"));
        if (adminDetails.getRole().equals("super_admin"))
            return ResponseEntity.ok(concursRepo.concursAllName());
        return ResponseEntity.ok(concursRepo.concursName(adminId));
    }

    public ResponseEntity<UserTotals> totals(Long adminId) {
        long fraud = 0L;
        long nonFraud;
        long total;
        List<UserDetails> usersList;
        AdminDetails admin = adminDetailsRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("admin topilmadi!"));
        if (admin.getRole().equals("admin"))
            usersList = userDetailsRepo.findByAdminId(adminId);
        else
            usersList = userDetailsRepo.findAll();

        for (UserDetails userDetails : usersList) {
            if (userDetails.getIsFraud())
                fraud = fraud + 1;
        }
        total = usersList.size();
        nonFraud = total - fraud;
        return ResponseEntity.ok(
                new UserTotals(total, fraud, nonFraud));
    }

    public ResponseEntity<List<UserDetails>> search(
            Long adminId, String key, String searchField) {
        List<String> list;
        List<LinkedUsers> linkedUsers;
        List<UserDetails> userDetailsList;

        AdminDetails admin = adminDetailsRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("admin topilmadi!"));

        if (admin.getRole().equals("super_admin")) {
            if (searchField.equals("phone")) {
                userDetailsList = userDetailsRepo.findByUserPhoneAll(key);
            }
            else {
                linkedUsers = linkedUsersRepo.findByUserCode(key);
                list = linkedUsers.stream().map(LinkedUsers::getUserPhone).toList();
                userDetailsList = userDetailsRepo.findByUserPhone(list);
            }
        }
        else {
            if (searchField.equals("phone"))
                userDetailsList = userDetailsRepo.findByUserPhoneAll(key, adminId);
            else {
                linkedUsers = linkedUsersRepo.findByUserCode(key);
                list = linkedUsers.stream().map(LinkedUsers::getUserPhone).toList();
                List<UserDetails> byUserPhone = userDetailsRepo.findByUserPhone(list);
                userDetailsList = byUserPhone.stream()
                        .filter(v -> v.getAdminId().equals(adminId))
                        .toList();
            }
        }
        return ResponseEntity.ok(userDetailsList);
    }
}
