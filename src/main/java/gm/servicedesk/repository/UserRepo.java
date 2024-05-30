package gm.servicedesk.repository;

import gm.servicedesk.model.Org;
import gm.servicedesk.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    public List<User> findByOrg(Org org);

    public void deleteByIdAndOrg(Integer id, Org org);
}
