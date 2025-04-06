package org.example.bxbatuz.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileResponse implements ProfileResponse{
    private Long id;
    private String name;
    private int empCount;
    private String email;
    private String address;
}
