package org.example.bxbatuz.Dto;

import org.example.bxbatuz.Enum.LeaveType;

public record LeaveRequestDto(
        Long userId,
        LeaveType leaveType,
        String reason

) {
}
