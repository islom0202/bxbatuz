package org.example.bxbatuz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;
}