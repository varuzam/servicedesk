package gm.servicedesk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gm.servicedesk.dto.UserAddReq;
import gm.servicedesk.model.Org;
import gm.servicedesk.model.User;
import gm.servicedesk.repository.OrgRepo;
import gm.servicedesk.repository.UserRepo;

@Service
public class UserService {

    static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepo repo;
    private final OrgRepo orgRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo repo, OrgRepo orgRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.orgRepo = orgRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public List<User> findByOrg(Org org) {
        return repo.findByOrg(org);
    }

    public User addUser(UserAddReq req) {
        User user = new User();
        user.setUsername(req.username());
        user.setFullname(req.fullname());
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setRole(req.role());

        Org org = orgRepo.findIdByName(req.org());
        if (org != null) {
            user.setOrg(org);
        }
        return repo.save(user);
    }
}
