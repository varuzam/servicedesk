package gm.servicedesk.job;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gm.servicedesk.model.UserInvite;
import gm.servicedesk.repository.UserInviteRepo;

@Component
public class CleanUp {

    static final long INVITE_DURATION_HOURS = 24;
    static final Logger log = LoggerFactory.getLogger(CleanUp.class);

    @Autowired
    private UserInviteRepo userInviteRepo;

    @Scheduled(fixedRate = 7200000)
    @Transactional
    public void deleteExpiredInvites() {
        List<UserInvite> invites = userInviteRepo
                .deleteByCreatedAtBefore(LocalDateTime.now().minusHours(INVITE_DURATION_HOURS));
        log.info("{} expired invites has beed deleted", invites.size());
    }
}
