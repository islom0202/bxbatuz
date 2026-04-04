package org.example.bxbatuz.antifraud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTotals {
    private Long total;
    private Long fraud;
    private Long nonFraud;
}
