package gm.servicedesk.dto;

import jakarta.validation.constraints.*;

public record UserRegisterReq(
        @NotBlank(message = "Invalid username") @Size(min = 3, max = 32) String username,
        @NotBlank(message = "Invalid fullname") @Size(min = 6, max = 32) String fullname,
        @Email @Size(max = 32) String email,
        @NotBlank(message = "Invalid password") @Size(min = 8, max = 32) String password) {
}
