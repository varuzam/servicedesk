package gm.servicedesk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String subject;

    String desc;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TicketStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TicketPriority priority;

    @ManyToOne()
    @JoinColumn(name = "org_id")
    Org org;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
