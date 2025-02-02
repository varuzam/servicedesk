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

import gm.servicedesk.exception.ResourceNotFoundException;
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
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name" + username));
        return user;
    }

    // used by Spring Security for oauth2Login
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = (new DefaultOAuth2UserService()).loadUser(userRequest);
        if (oauth2User.getAttribute("login") == null)
            throw new ResourceNotFoundException("The attr 'login' not found in Oauth2 payload");
        String oauthLogin = oauth2User.getAttribute("login").toString();
        User user = repo.findByUsername(oauthLogin)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name" + oauthLogin));
        return user;
    }
}
