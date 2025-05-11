package com.example.bank.services;

import com.example.bank.domain.Loan;
import com.example.bank.domain.ROLE;
import com.example.bank.domain.User;
import com.example.bank.dto.UserDTO;
import com.example.bank.exception.LoanException;
import com.example.bank.mapper.UserMapper;
import com.example.bank.repository.LoanInstallmentRepository;
import com.example.bank.repository.UserRepository;
import com.example.bank.service.LoanInstallmentService;
import com.example.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserService userService;

    @BeforeEach
    void setUpCommon(){
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository,passwordEncoder);
    }

    @Test
    void registerUserButAlreadyExist(){
        UserDTO user = new UserDTO("admin", "admin");
        when(userRepository.existsByUsername("admin")).thenReturn(true);
        LoanException exception = assertThrows(LoanException.class, () -> userService.registerUser(user));
        assertEquals("Username must be unique!!", exception.getMessage());
    }

    @Test
    void registerUser(){
        UserDTO user = new UserDTO("admin", "admin");
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        userService.registerUser(user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void loadUserButDoesNotExist(){
        when(userRepository.findByUsername("ali")).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("ali"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testLoadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        User user = new User();
        user.setUsername("ali");
        user.setPassword("1234");
        user.setRole(ROLE.USER.USER);

        when(userRepository.findByUsername("ali")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userService.loadUserByUsername("ali");

        // Assert
        assertEquals("ali", userDetails.getUsername());
        assertEquals("1234", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")));

        verify(userRepository).findByUsername("ali");
    }
}
