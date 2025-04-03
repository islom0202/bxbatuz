package org.example.bxbatuz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.bxbatuz.Enum.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance extends BaseEntity {
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Employees employees;

    @Column
    private LocalDate date;

    @Column
    private LocalTime checkIn;

    @Column
    private LocalTime checkOut;

    @Column
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column
    private double workedHours;

    @Column
    private String notes;

    public Attendance(Employees employees,
                      LocalDate date,
                      String note,
                      LocalTime checkIn,
                      AttendanceStatus status) {
        this.employees = employees;
        this.date = date;
        this.notes = note;
        this.checkIn = checkIn;
        this.status = status;
    }
}
