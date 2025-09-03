package com.bank.user.user_service.controller;

import com.bank.user.user_service.dto.LoginRequest;
import com.bank.user.user_service.dto.LoginResponse;
import com.bank.user.user_service.dto.UserRegisterRequest;
import com.bank.user.user_service.dto.UserRegisterResponse;
import com.bank.user.user_service.entity.User;
import com.bank.user.user_service.service.AuthService;
import com.bank.user.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Handles user registration request.
     *
     * @param request the user registration request containing the username, email, and password.
     * @return a response entity with the registration response and the status {@code CREATED}.
     */
    @PostMapping("/register")
  public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody
      UserRegisterRequest request) {
    log.info("user register as username: {}", request.getUsername());
    var response = userService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

    /**
     * Handle user login request.
     *
     * @param request the user login request with username/email and password
     * @return a response entity with the login response containing the JWT token
     */
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody
      LoginRequest request) {
    log.info("Login attempt for user/email: {}", request.getUsernameOrEmail());
    LoginResponse response = authService.loginUser(request);
    return ResponseEntity.ok(response);
  }

  /**
   * Get all users (Admin only)
   * @return List of all users
   */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
