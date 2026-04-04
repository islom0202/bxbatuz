package org.example.bxbatuz.antifraud.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bxbatuz.antifraud.contraints.ResponseMsg;
import org.example.bxbatuz.antifraud.dto.FormReq;
import org.example.bxbatuz.antifraud.dto.LocationStats;
import org.example.bxbatuz.antifraud.entity.Links;
import org.example.bxbatuz.antifraud.entity.UserDetails;
import org.example.bxbatuz.antifraud.repo.LinkRepo;
import org.example.bxbatuz.antifraud.repo.LinkUsersRepo;
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
    private final UserDetailsRepo userDetailsRepo;
    private final FraudLoggingService fraudLoggingService;

    @Transactional
    public ResponseEntity<String> submitRegistration(FormReq dto, String ipAddress) {
        Links link = getLink(BASE_URI.getVal().concat(dto.getAdminId()));
        LocationStats ipStats = getIpLocation(ipAddress);
        log.info("IP Address: {}", ipAddress);

        UserDetails userDetail = checkIsFraudAndExist(dto);
        if (userDetail != null && userDetail.getIsFraud()) {
            throw new RuntimeException("Siz maxinator sifatida aniqlandingiz!");
        }

        checkCurrentLocation(dto, ipAddress, ipStats, link);

        checkSameConcursAndLocationFromDb(dto, ipAddress, ipStats, link);

        userService.logUserAndLink(userDetail,dto, ipAddress, ipStats, link);

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

    public UserDetails checkIsFraudAndExist(FormReq dto) {
        UserDetails user;
        if (dto.getDeviceId() != null)
            user = userDetailsRepo.findByUserPhoneOrUserDeviceId(dto.getPhone(), dto.getDeviceId());
        else
            user = userDetailsRepo.findByUserPhone(dto.getPhone());

        return user;
    }

    public void checkSameConcursAndLocationFromDb(FormReq dto, String ipAddress, LocationStats ipStats, Links link) {

        if (linkRepo.isExist(link.getConcursId(), dto.getPhone(), dto.getDeviceId())) {
            throw new RuntimeException(DEVICE_DUPLICATED.getMessage());
        }

        if (userDetailsRepo.isAreaOccupied(link.getConcursId(), dto.getLatitude(), dto.getLongitude())) {
            fraudLoggingService.logFraud(
                    dto,
                    ipAddress,
                    ResponseMsg.AREA_ALREADY_OCCUPIED_LOG.getMessage(),
                    ipStats,
                    link);
            throw new RuntimeException(ResponseMsg.AREA_ALREADY_OCCUPIED.getMessage());
        }
    }

    public void checkCurrentLocation(FormReq dto, String ipAddress, LocationStats ipStats, Links link) {
        double distanceBetweenGpsAndIp = calculateDistance(dto.getLatitude(), dto.getLongitude(),
                ipStats.getLatitude(), ipStats.getLongitude());

        boolean isSuspicious = distanceBetweenGpsAndIp >= 15; // Over 15km is flagged

        if (isSuspicious) {
            fraudLoggingService.logFraud(
                    dto,
                    ipAddress,
                    String.format(ResponseMsg.LOCATION_MISMATCH_LOG.getMessage(), distanceBetweenGpsAndIp),
                    ipStats,
                    link);

            throw new RuntimeException(ResponseMsg.LOCATION_MISMATCH.getMessage());
        }
    }

    public void verifyLocationConsistency(FormReq dto, String ipAddress, LocationStats ipStats, Links link) {
        double distanceBetweenGpsAndIp = calculateDistance(dto.getLatitude(), dto.getLongitude(),
                ipStats.getLatitude(), ipStats.getLongitude());

        boolean isSuspicious = distanceBetweenGpsAndIp >= 15; // Over 15km is flagged

        // 2. Check for Fraud/Duplicates in DB
        if (userDetailsRepo.existsByUserPhone(dto.getPhone())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ResponseMsg.PHONE_ALREADY_REGISTERED.getMessage()
            );
        }

        if (userDetailsRepo.existsByUserDeviceId(dto.getDeviceId())) {
            fraudLoggingService.logFraud(dto, ipAddress, DEVICE_DUPLICATED.getMessage(), ipStats, link);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    DEVICE_DUPLICATED.getMessage()
            );
        }

//        if (userDetailsRepo.isAreaOccupied(dto.getLatitude(), dto.getLongitude())) {
//            fraudLoggingService.logFraud(dto, ipAddress, ResponseMsg.AREA_ALREADY_OCCUPIED_LOG.getMessage(), ipStats, link);
//            throw new ResponseStatusException(
//                    HttpStatus.FORBIDDEN,
//                    ResponseMsg.AREA_ALREADY_OCCUPIED.getMessage()
//            );
//        }

        if (isSuspicious) {
            fraudLoggingService.logFraud(dto, ipAddress, String.format(ResponseMsg.LOCATION_MISMATCH_LOG.getMessage(), distanceBetweenGpsAndIp), ipStats, link);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    ResponseMsg.LOCATION_MISMATCH.getMessage()
            );
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
