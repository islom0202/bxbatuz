package org.example.bxbatuz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employees implements Serializable {

    @Id
    private Long id;

    @Column
    private String fullName;

    @Column
    private String phone;

    @Column
    private String position;

    @JoinColumn
    private Double officialWorkHours;

    @JoinColumn
    private LocalTime startTime;

    @JoinColumn
    private LocalTime endTime;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Department department;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Auth auth;
}