package org.example.bxbatuz.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Data
public class Company implements Serializable {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private int empCount;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;
}
