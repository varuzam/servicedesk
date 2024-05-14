package gm.servicedesk.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "org")
public class Org {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String name;

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL)
    private List<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}