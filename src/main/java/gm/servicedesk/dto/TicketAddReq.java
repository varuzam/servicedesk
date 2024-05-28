package gm.servicedesk.dto;

import gm.servicedesk.model.TicketPriority;

import jakarta.validation.constraints.*;

public record TicketAddReq(
        @NotBlank(message = "Invalid subject") @Size(min = 6, max = 32) String subject,
        @NotBlank(message = "Invalid description") @Size(max = 255) String desc,
        @NotNull(message = "Invalid priority") TicketPriority priority) {
}
