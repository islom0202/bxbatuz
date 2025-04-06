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
public class Company implements Serializable {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private int empCount;

    @JoinColumn
    private String address;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Auth auth;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private Files image;

    public Company(Long id,
                   String name,
                   int empCount,
                   String address,
                   Auth auth) {
        this.id = id;
        this.name = name;
        this.empCount = empCount;
        this.address = address;
        this.auth = auth;
    }
}
