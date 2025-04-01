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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;
}
