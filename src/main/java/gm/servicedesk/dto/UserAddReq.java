package gm.servicedesk.dto;

import gm.servicedesk.model.Role;

import jakarta.validation.constraints.*;

public record UserAddReq(
        @NotBlank(message = "Invalid username") @Size(min = 3, max = 32) String username,
        @NotBlank(message = "Invalid fullname") @Size(min = 6, max = 32) String fullname,
        @Email @Size(max = 32) String email,
        @NotBlank(message = "Invalid password") @Size(min = 8, max = 32) String password,
        @NotNull(message = "Invalid role") Role role,
        @NotBlank(message = "Invalid org") String org) {
}
