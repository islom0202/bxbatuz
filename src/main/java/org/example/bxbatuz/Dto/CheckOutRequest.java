package org.example.bxbatuz.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CheckOutRequest(
        Long userId,
        LocalDate date,
        String note,
        LocalTime checkOut
) {
}
