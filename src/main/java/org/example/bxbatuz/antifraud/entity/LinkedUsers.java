package org.example.bxbatuz.antifraud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "linked_users")
public class LinkedUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long linkId;
    private String userCode;
    private Timestamp sentAt;
    private Timestamp clickedAt;
    private Long concursId;
    private String userPhone;
    private String userDeviceId;
    private Double latitude;
    private Double longitude;
    private Double ipLatitude;
    private Double ipLongitude;
}
