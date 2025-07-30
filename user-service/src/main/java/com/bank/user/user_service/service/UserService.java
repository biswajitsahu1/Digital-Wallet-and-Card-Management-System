package com.bank.user.user_service.service;

import com.bank.user.user_service.dto.UserRegisterRequest;
import com.bank.user.user_service.dto.UserRegisterResponse;
import com.bank.user.user_service.entity.User;
import com.bank.user.user_service.exception.UserAlreadyExistsException;
import com.bank.user.user_service.mapper.UserMapper;
import com.bank.user.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ClassUtils.isPresent;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        log.info("Registration attempt for username: {}, email: {}", request.getUsername(), request.getEmail());
        validateUniqueUser(request.getUsername(), request.getEmail());

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("Registration successful for userId: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    private void validateUniqueUser(String username, String email) {
        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);

        if (usernameExists || emailExists) {
            log.warn("Registration failed - Duplicate {}{}",
                    usernameExists ? "username" : "",
                    emailExists ? (usernameExists ? " and email" : "email") : ""
            );

            throw new UserAlreadyExistsException(
                    usernameExists && emailExists ? "Username and email already exist"
                            : usernameExists ? "Username already exists"
                            : "Email already exists"
            );
        }
    }


}
