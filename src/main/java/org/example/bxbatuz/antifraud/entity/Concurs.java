package org.example.bxbatuz.antifraud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@Entity
@Table(name = "concurs")
@NoArgsConstructor
@AllArgsConstructor
public class Concurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private Timestamp createdAt;
    private Long adminId;
}
