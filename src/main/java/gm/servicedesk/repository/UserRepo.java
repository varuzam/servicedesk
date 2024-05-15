package gm.servicedesk.repository;

import gm.servicedesk.model.Org;
import gm.servicedesk.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

    public List<User> findByOrg(Org org);
}
