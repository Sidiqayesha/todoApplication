package com.todo.services.impl;

import com.todo.config.AuthenticationRequest;
import com.todo.model.RegistrationResponse;
import com.todo.model.Users;
import com.todo.repository.UserRepo;
import com.todo.services.JwtService;
import com.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public RegistrationResponse createUser(Users user) {
        if (!isUsernameAvailable(user.getUsername())) {
            return new RegistrationResponse(false, "Username already exists");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new RegistrationResponse(true, "User registered successfully");
        } catch (DataIntegrityViolationException e) {
            return new RegistrationResponse(false, "Failed to register user: Username already exists");
        } catch (Exception e) {
            return new RegistrationResponse(false, "Failed to register user: " + e.getMessage());
        }
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    @Override
    public String login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        return jwtService.generateToken(request.getUsername());
    }
}
