package gm.servicedesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import gm.servicedesk.model.User;
import gm.servicedesk.repository.UserRepo;

@Service
public class UserAuthService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    static final Logger log = LoggerFactory.getLogger(UserAuthService.class);
    private final UserRepo repo;

    public UserAuthService(UserRepo repo) {
        this.repo = repo;
    }

    // used by Spring Security for formLogin & httpBasic
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in DB with name" + username);
        }
        return user;
    }

    // used by Spring Security for oauth2Login
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = (new DefaultOAuth2UserService()).loadUser(userRequest);
        User user = repo.findByUsername(oauth2User.getAttribute("login").toString());
        if (user == null) {
            log.warn("The username {} does not exist in DB", oauth2User.getAttribute("login").toString());
            throw new UsernameNotFoundException("The username does not exist in DB");
        }
        return user;
    }
}
