package org.example.bxbatuz.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CheckInRequest(
        Long userId,
        LocalDate date,
        String note,
        LocalTime checkIn
) {
}
