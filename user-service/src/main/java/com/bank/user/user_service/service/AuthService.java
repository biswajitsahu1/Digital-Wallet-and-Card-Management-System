package com.bank.user.user_service.service;

import com.bank.user.user_service.dto.LoginRequest;
import com.bank.user.user_service.dto.LoginResponse;
import com.bank.user.user_service.dto.UserRegisterResponse;
import com.bank.user.user_service.entity.User;
import com.bank.user.user_service.exception.InvalidCredentialsException;
import com.bank.user.user_service.mapper.UserMapper;
import com.bank.user.user_service.repository.UserRepository;
import com.bank.user.user_service.util.JwtUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Builder
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  public LoginResponse loginUser(LoginRequest request) {
    Optional<User> optionalUser = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(request.getUsernameOrEmail(),
        request.getUsernameOrEmail());

    if(optionalUser.isEmpty()){
      log.warn("Login failed: user {} not found",request.getUsernameOrEmail());
      throw new InvalidCredentialsException("Invalid username/email or password");
    }
    User user = optionalUser.get();
    if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
      log.warn("Login failed: invalid password for user '{}'", request.getUsernameOrEmail());
      throw new InvalidCredentialsException("Invalid username/email or password");
    }
    String jwt = jwtUtil.generateToken(user);
    log.info("Login successful for {}", user.getUsername());

    return LoginResponse.builder()
        .token(jwt)
        .build();
  }
}
