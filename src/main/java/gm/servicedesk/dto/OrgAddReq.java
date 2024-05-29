package gm.servicedesk.dto;

import jakarta.validation.constraints.*;

public record OrgAddReq(
        @NotBlank(message = "Invalid name") @Size(min = 3, max = 32) String name) {
}
