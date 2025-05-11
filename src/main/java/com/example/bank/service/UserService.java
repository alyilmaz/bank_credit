package com.example.bank.service;

import com.example.bank.domain.ROLE;
import com.example.bank.domain.User;
import com.example.bank.dto.UserDTO;
import com.example.bank.exception.LoanException;
import com.example.bank.mapper.UserMapper;
import com.example.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void registerUser(UserDTO user){
        boolean isExist = userRepository.existsByUsername(user.username());
        if (isExist) {
            throw new LoanException("Username must be unique!!");
        }
        User userEntity = UserMapper.INSTANCE.toEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(ROLE.ADMIN);
        userRepository.save(userEntity);
        log.info("New user is created");
    }

}

