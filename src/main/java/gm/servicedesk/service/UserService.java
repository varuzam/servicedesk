package gm.servicedesk.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gm.servicedesk.dto.UserAddReq;
import gm.servicedesk.dto.UserProfileUpdateReq;
import gm.servicedesk.dto.UserRegisterReq;
import gm.servicedesk.exception.*;
import gm.servicedesk.model.Org;
import gm.servicedesk.model.Role;
import gm.servicedesk.model.User;
import gm.servicedesk.model.UserInvite;
import gm.servicedesk.repository.OrgRepo;
import gm.servicedesk.repository.UserInviteRepo;
import gm.servicedesk.repository.UserRepo;

@Service
public class UserService {

    static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepo repo;
    private final UserInviteRepo userInviterepo;
    private final OrgRepo orgRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo repo, UserInviteRepo userInviteRepo, OrgRepo orgRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userInviterepo = userInviteRepo;
        this.orgRepo = orgRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public List<User> findAll(Org org) {
        return repo.findByOrg(org);
    }

    public User find(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User add(UserAddReq req) {
        User user = new User();
        user.setUsername(req.username());
        user.setFullname(req.fullname());
        if (!req.email().equals("")) // email is optional
            user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setRole(req.role());

        Org org = orgRepo.findByName(req.org());
        if (org != null) {
            user.setOrg(org);
        }
        return repo.save(user);
    }

    public String generateInviteToken(Org org) {
        var token = UUID.randomUUID().toString();
        var invite = new UserInvite();
        invite.setToken(token);
        invite.setOrg(org);
        userInviterepo.save(invite);
        return token;
    }

    public User registerUserByInviteToken(String token, UserRegisterReq req) {
        UserInvite invite = userInviterepo.findOneByToken(token);
        if (invite == null) {
            return null;
        }
        User user = new User();
        user.setUsername(req.username());
        user.setFullname(req.fullname());
        if (!req.email().equals(""))
            user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setRole(Role.CUSTOMER); // registration available only for Role.CUSTOMER

        Org org = orgRepo.findByName(invite.getOrg().getName());
        if (org == null) {
            return null;
        }
        user.setOrg(org);
        if (repo.save(user) == null) {
            return null;
        } else {
            userInviterepo.delete(invite);
        }

        return user;
    }

    public User update(Integer id, UserAddReq req) {
        User user = find(id);
        user.setUsername(req.username());
        user.setFullname(req.fullname());
        if (!req.email().equals("")) // email is optional
            user.setEmail(req.email());
        user.setRole(req.role());

        Org org = orgRepo.findByName(req.org());
        if (org != null) {
            user.setOrg(org);
        }
        return repo.save(user);
    }

    public void update(Integer id, UserProfileUpdateReq req) {
        User user = find(id);
        user.setUsername(req.username());
        user.setFullname(req.fullname());
        if (!req.email().equals("")) // email is optional
            user.setEmail(req.email());
        repo.save(user);
    }

    public void updatePassword(Integer id, String password) {
        User user = find(id);
        user.setPassword(passwordEncoder.encode(password));
        repo.save(user);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Transactional
    public void delete(Integer id, Org org) {
        repo.deleteByIdAndOrg(id, org);
    }
}
