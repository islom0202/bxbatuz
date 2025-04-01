package org.example.bxbatuz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity{
    @Column
    private String name;
}
