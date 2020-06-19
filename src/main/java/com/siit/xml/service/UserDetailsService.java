package com.siit.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.log.SysoCounter;
import com.siit.xml.modelUser.User;
import com.siit.xml.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user == null) throw new UsernameNotFoundException("No user found");
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_AUTHOR"));
        if(user.getRole().toString().equals("ROLE_REVIEWER")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_REVIEWER"));
        }
        if(user.getRole().toString().equals("ROLE_EDITOR")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_REVIEWER"));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EDITOR"));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername().toString(),
                user.getPassword().toString(),
                grantedAuthorities);
    }
}
