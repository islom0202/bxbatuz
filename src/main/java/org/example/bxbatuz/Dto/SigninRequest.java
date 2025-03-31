package org.example.bxbatuz.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SigninRequest(
        @NotBlank(message = "email is required")
        @Size(min = 6)
        String email,
        @NotBlank(message = "password is required")
        @Size(min = 4, message = "password must be 4 character at least")
        String password
) {
}
