package com.bank.user.user_service.service;

import com.bank.user.user_service.dto.UserRegisterRequest;
import com.bank.user.user_service.dto.UserRegisterResponse;
import com.bank.user.user_service.entity.User;
import com.bank.user.user_service.exception.UserAlreadyExistsException;
import com.bank.user.user_service.integration.EmailClient;
import com.bank.user.user_service.mapper.UserMapper;
import com.bank.user.user_service.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailClient emailClient;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       EmailClient emailClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailClient = emailClient;
    }

    @Transactional
    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        log.info("Registration attempt: username='{}', email='{}'", request.getUsername(), request.getEmail());

        checkDuplicate(request.getUsername(), request.getEmail());

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        sendWelcomeEmail(savedUser);

        log.info("Registration successful for userId={}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    private void checkDuplicate(String username, String email) {
        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);

        if (usernameExists || emailExists) {
            String msg;
            if (usernameExists && emailExists) {
                msg = "Username and email already exist";
            } else if (usernameExists) {
                msg = "Username already exists";
            } else {
                msg = "Email already exists";
            }
            log.warn("Registration failed - {}", msg);
            throw new UserAlreadyExistsException(msg);
        }
    }

    private void sendWelcomeEmail(User user) {
        try {
            emailClient.sendSuccessRegistrationEmail(user.getEmail(), user.getUsername());
        } catch (MessagingException e) {
            log.error("Failed to send registration email to {}: {}", user.getEmail(), e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
