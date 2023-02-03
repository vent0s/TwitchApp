package com.septim.twitchapp.user;

import com.septim.twitchapp.db.UserRepository;
import com.septim.twitchapp.db.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserDetailsManager userDetailsManager;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;

    public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(String username, String password, String firstName, String lastName) {
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USERS")
                .build();
        userDetailsManager.createUser(user);
        userRepository.updateNameByUsername(username,firstName,lastName);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
