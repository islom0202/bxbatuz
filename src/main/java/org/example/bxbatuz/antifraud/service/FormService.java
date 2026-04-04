package org.example.bxbatuz.antifraud.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bxbatuz.antifraud.contraints.ResponseMsg;
import org.example.bxbatuz.antifraud.dto.FormReq;
import org.example.bxbatuz.antifraud.dto.LocationStats;
import org.example.bxbatuz.antifraud.entity.LinkedUsers;
import org.example.bxbatuz.antifraud.entity.Links;
import org.example.bxbatuz.antifraud.repo.LinkRepo;
import org.example.bxbatuz.antifraud.repo.LinkedUsersRepo;
import org.example.bxbatuz.antifraud.repo.UserDetailsRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;

import static org.example.bxbatuz.antifraud.contraints.ResponseMsg.DEVICE_DUPLICATED;
import static org.example.bxbatuz.antifraud.contraints.UriEnum.BASE_URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormService {
    private final LinkRepo linkRepo;
    private final UserService userService;
    private final DatabaseReader databaseReader;
    private final LinkedUsersRepo linkedUsersRepo;
    private final UserDetailsRepo userDetailsRepo;
    private final FraudLoggingService fraudLoggingService;

    @Transactional
    public ResponseEntity<String> submitRegistration(FormReq dto, String ipAddress) {
        Links link = getLink(BASE_URI.getVal().concat(dto.getAdminId()));
        LocationStats ipStats = getIpLocation(ipAddress);
        log.info("IP Address: {}", ipAddress);

        LinkedUsers linkedUser = checkIsFraudAndExist(dto, link);
        if (linkedUser != null && linkedUser.getIsFraud()) {
            throw new RuntimeException("Siz maxinator sifatida aniqlandingiz!");
        }

        checkCurrentLocation(dto, ipAddress, ipStats, link, linkedUser);

        checkSameConcursAndLocationFromDb(dto, ipAddress, ipStats, link, linkedUser);

        userService.logUserAndLink(linkedUser, dto, ipAddress, ipStats, link, linkedUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseMsg.SUCCESS.getMessage());
    }

    private LocationStats getIpLocation(String ip) {
        try {
            CityResponse res = databaseReader.city(InetAddress.getByName(ip));
            return new LocationStats(res.getLocation().getLatitude(), res.getLocation().getLongitude());
        } catch (Exception e) {
            return new LocationStats(0, 0);
        }
    }

    public LinkedUsers checkIsFraudAndExist(FormReq dto, Links link) {
        LinkedUsers user;
        if (dto.getDeviceId() != null)
            user = linkedUsersRepo.findByUserPhoneOrUserDeviceId(
                    link.getConcursId(), dto.getPhone(), dto.getDeviceId());
        else
            user = linkedUsersRepo.findByUserPhone(dto.getPhone());
        return user;
    }

    public void checkSameConcursAndLocationFromDb(
            FormReq dto, String ipAddress, LocationStats ipStats, Links link, LinkedUsers linkedUser) {

        if (linkRepo.isExist(link.getConcursId(), dto.getPhone(), dto.getDeviceId())) {
            fraudLoggingService.logFraud(
                    dto,
                    ipAddress,
                    ResponseMsg.DEVICE_DUPLICATED_LOG.getMessage(),
                    ipStats,
                    link,
                    linkedUser);
            throw new RuntimeException(DEVICE_DUPLICATED.getMessage());
        }

        if (userDetailsRepo.isAreaOccupied(link.getConcursId(), dto.getLatitude(), dto.getLongitude())) {
            System.out.println("isAreaOccupied: " + dto.getPhone());
            fraudLoggingService.logFraud(
                    dto,
                    ipAddress,
                    ResponseMsg.AREA_ALREADY_OCCUPIED_LOG.getMessage(),
                    ipStats,
                    link,
                    linkedUser);
            throw new RuntimeException(ResponseMsg.AREA_ALREADY_OCCUPIED.getMessage());
        }
    }

    public void checkCurrentLocation(
            FormReq dto, String ipAddress, LocationStats ipStats, Links link, LinkedUsers linkedUser) {
        double distanceBetweenGpsAndIp = calculateDistance(dto.getLatitude(), dto.getLongitude(),
                ipStats.getLatitude(), ipStats.getLongitude());
        System.out.println("distanceBetweenGpsAndIp: " + distanceBetweenGpsAndIp + "username: " +  dto.getPhone());
        boolean isSuspicious = distanceBetweenGpsAndIp >= 2000; // Over 400km is flagged

        if (isSuspicious) {
            fraudLoggingService.logFraud(
                    dto,
                    ipAddress,
                    String.format(ResponseMsg.LOCATION_MISMATCH_LOG.getMessage(), distanceBetweenGpsAndIp),
                    ipStats,
                    link,
                    linkedUser);

            throw new RuntimeException(ResponseMsg.LOCATION_MISMATCH.getMessage());
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in KM
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private Links getLink(String fullLink) {
        Links links = linkRepo.findByGeneratedLink(fullLink);
        if (links.getIsExpired()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ResponseMsg.LINK_EXPIRED.getMessage()
            );
        }
        return links;
    }
}
