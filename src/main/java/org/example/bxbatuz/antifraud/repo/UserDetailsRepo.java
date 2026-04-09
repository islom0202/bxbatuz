package org.example.bxbatuz.antifraud.repo;

import org.example.bxbatuz.antifraud.entity.UserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {
    Page<UserDetails> findByIsFraud(Boolean isFraud, Pageable pageable);

    boolean existsByUserPhone(String phone);
    UserDetails findByUserPhone(String phone);
    @Query(value = """
            select * from user_details where user_phone=:phone and admin_id=:adminId""", nativeQuery = true)
    List<UserDetails> findByUserPhoneAll(@Param("phone") String phone, @Param("adminId") Long adminId);

    @Query(value = """
    SELECT EXISTS (
        SELECT 1 FROM linked_users u
        WHERE
            u.concurs_id=:concursId and
            u.latitude  BETWEEN :lat - (1000.0 / 111320.0)
                            AND :lat + (1000.0 / 111320.0)
            AND
            u.longitude BETWEEN :lon - (1000.0 / (111320.0 * COS(RADIANS(:lat))))
                            AND :lon + (1000.0 / (111320.0 * COS(RADIANS(:lat))))
            AND (2 * 6371000 * ASIN(SQRT(
                    POWER(SIN(RADIANS(u.latitude  - :lat) / 2), 2)
                  + COS(RADIANS(:lat))
                  * COS(RADIANS(u.latitude))
                  * POWER(SIN(RADIANS(u.longitude - :lon) / 2), 2)
            ))) <= 1000
    )
    """, nativeQuery = true)
    boolean isAreaOccupied(@Param("concursId") Long concursId, @Param("lat") double lat, @Param("lon") double lon);

//    @Query(value = "SELECT EXISTS(SELECT 1 FROM user_details u " +
//            "WHERE (6371000 * acos(cos(radians(:lat)) * cos(radians(u.latitude)) * " +
//            "cos(radians(u.longitude) - radians(:lon)) + " +
//            "sin(radians(:lat)) * sin(radians(u.latitude)))) <= 1000)",
//            nativeQuery = true)
//    boolean isAreaOccupied(@Param("lat") double lat, @Param("lon") double lon);

    boolean existsByUserDeviceId(String deviceId);

    UserDetails findByUserDeviceId(String userDeviceId);
    List<UserDetails> findByAdminId(Long adminId);

    Page<UserDetails> findByAdminIdAndIsFraud(Long adminId, Boolean isExpired, Pageable pageable);
}
