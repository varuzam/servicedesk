package gm.servicedesk.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "user_invite")
public class UserInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String token;

    @ManyToOne()
    @JoinColumn(name = "org_id")
    Org org;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String username) {
        this.token = username;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime date) {
        this.createdAt = date;
    }

    @PrePersist
    void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
    }
}