package com.sleepy.zeo.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "scn-user-service")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        if ("sleepy".equals(username)) {
            userDetails.setUsername(username);
            userDetails.setPassword("1994");
            userDetails.setAccountNonLocked(true);
            userDetails.setEnabled(true);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("R"));
            userDetails.setAuthorities(authorities);
        }

        if ("steven".equals(username)) {
            userDetails.setUsername(username);
            userDetails.setPassword("940911");
            userDetails.setAccountNonLocked(true);
            userDetails.setEnabled(true);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("R"));
            authorities.add(new SimpleGrantedAuthority("D"));
            userDetails.setAuthorities(authorities);
        }

        if ("fcq".equals(username)) {
            userDetails.setUsername(username);
            userDetails.setPassword("1997");
            userDetails.setAccountNonLocked(true);
            userDetails.setEnabled(true);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("C"));
            authorities.add(new SimpleGrantedAuthority("U"));
            authorities.add(new SimpleGrantedAuthority("R"));
            authorities.add(new SimpleGrantedAuthority("D"));
            userDetails.setAuthorities(authorities);
        }

        return userDetails;
    }
}
