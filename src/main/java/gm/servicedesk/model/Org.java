package gm.servicedesk.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "org")
@Getter
@Setter
public class Org {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String name;

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL)
    private List<UserInvite> userInvites;
}
