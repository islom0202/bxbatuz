package org.example.bxbatuz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.bxbatuz.Enum.LeaveType;
import org.example.bxbatuz.Enum.RequestStatus;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest extends BaseEntity{
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Employees employees;

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Attendance attendance;

    @Column
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Column
    private String reason;
}
