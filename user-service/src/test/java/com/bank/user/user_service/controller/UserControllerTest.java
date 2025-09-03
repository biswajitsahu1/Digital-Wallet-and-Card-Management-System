package com.bank.user.user_service.controller;

import com.bank.user.user_service.dto.UserRegisterRequest;
import com.bank.user.user_service.dto.UserRegisterResponse;
import com.bank.user.user_service.exception.UserAlreadyExistsException;
import com.bank.user.user_service.service.AuthService;
import com.bank.user.user_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    void setUpLogger() {
        logAppender = new ListAppender<>();
        logAppender.start();
        Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
        logger.addAppender(logAppender);
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("Abcd123!")
                .build();

        UserRegisterResponse response = new UserRegisterResponse();
        response.setId(1L);
        response.setUsername("testuser");
        response.setEmail("test@example.com");

        when(userService.registerUser(any(UserRegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        boolean logFound = logAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage().contains("user register as username: testuser"));
        assert logFound : "Expected log message not found";
    }

    @Test
    void testRegisterUserDuplicateUsername() throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("Abcd123!")
                .build();

        when(userService.registerUser(any(UserRegisterRequest.class)))
                .thenThrow(new UserAlreadyExistsException("Username already exists"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    @Test
    void testRegisterUserInvalidPassword() throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("weak")
                .build();

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").exists());
    }

    @Test
    void testRegisterUserMissingFields() throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("testuser")
                .build();

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.password").exists());
    }

    @Test
    void testRegisterUserInvalidEmail() throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("testuser")
                .email("invalid-email")
                .password("Abcd123!")
                .build();

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"));

        verify(userService, never()).registerUser(any());
    }
}
