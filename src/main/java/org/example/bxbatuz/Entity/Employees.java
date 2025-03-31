package org.example.bxbatuz.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;
}