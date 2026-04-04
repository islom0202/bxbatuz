package org.example.bxbatuz.antifraud.service;

import lombok.RequiredArgsConstructor;
import org.example.bxbatuz.antifraud.dto.FormReq;
import org.example.bxbatuz.antifraud.dto.LocationStats;
import org.example.bxbatuz.antifraud.entity.LinkedUsers;
import org.example.bxbatuz.antifraud.entity.Links;
import org.example.bxbatuz.antifraud.entity.UserDetails;
import org.example.bxbatuz.antifraud.repo.LinkedUsersRepo;
import org.example.bxbatuz.antifraud.repo.UserDetailsRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudLoggingService {
    private final UserDetailsRepo userDetailsRepo;
    private final LinkedUsersRepo linkedUsersRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logFraud(
            FormReq dto, String ip, String reason, LocationStats ipStats, Links link, LinkedUsers linkedUser) {
        UserDetails fraudUser = new UserDetails();
        fraudUser.setUserPhone(dto.getPhone());
        fraudUser.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        fraudUser.setUserIp(ip);
        fraudUser.setIsFraud(true);
        fraudUser.setLogMessage(reason);
        fraudUser.setLatitude(dto.getLatitude());
        fraudUser.setLongitude(dto.getLongitude());
        fraudUser.setIpLatitude(ipStats.getLatitude());
        fraudUser.setIpLongitude(ipStats.getLongitude());
        fraudUser.setUserDeviceId(dto.getDeviceId());
        fraudUser.setAdminId(link.getAdminId());
        userDetailsRepo.save(fraudUser);

        LinkedUsers fraudLinkedUser = new LinkedUsers();
        fraudLinkedUser.setLinkId(link.getId());
        fraudLinkedUser.setUserId(fraudUser.getId());
        fraudLinkedUser.setIsFraud(true);
        fraudLinkedUser.setUserCode(dto.getCode());
        fraudLinkedUser.setUserPhone(dto.getPhone());
        fraudLinkedUser.setUserDeviceId(dto.getDeviceId());
        fraudLinkedUser.setConcursId(link.getConcursId());
        fraudLinkedUser.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        fraudLinkedUser.setClickedAt(Timestamp.valueOf(LocalDateTime.now()));
        fraudLinkedUser.setLatitude(dto.getLatitude());
        fraudLinkedUser.setLongitude(dto.getLongitude());
        fraudLinkedUser.setIpLatitude(ipStats.getLatitude());
        fraudLinkedUser.setIpLongitude(ipStats.getLongitude());
        linkedUsersRepo.save(fraudLinkedUser);
    }
}
