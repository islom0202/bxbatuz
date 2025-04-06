package org.example.bxbatuz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files extends BaseEntity{
    @Column
    private String name;

    @Column
    private String type;

    @Lob
    @Column
    private byte[] data;

    @Column
    private LocalDateTime uploadedAt;
}
