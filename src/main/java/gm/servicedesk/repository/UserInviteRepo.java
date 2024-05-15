package gm.servicedesk.repository;

import gm.servicedesk.model.UserInvite;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface UserInviteRepo extends JpaRepository<UserInvite, Integer> {
    UserInvite findOneByToken(String token);

    List<UserInvite> deleteByCreatedAtBefore(LocalDateTime date);
}
