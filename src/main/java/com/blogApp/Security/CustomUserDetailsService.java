package com.blogApp.Security;


import com.blogApp.entity.Role;
import com.blogApp.entity.User;
import com.blogApp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepo.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email:"+userNameOrEmail)
        );

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),user.getPassword(),
            convertRolesToAuthorities(user.getRoles())
        );
    }
    private Collection<? extends GrantedAuthority> convertRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
