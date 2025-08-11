package com.bank.user.user_service.controller;

import com.bank.user.user_service.dto.UserRegisterRequest;
import com.bank.user.user_service.dto.UserRegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
//register test
    @Test
    void registerUser() {
        UserRegisterRequest userRegisterRequest = UserRegisterRequest
                .builder()
                .username("test")
                .email("test@gmail.com")
                .password("test")
                .build();

        ResponseEntity<UserRegisterResponse> response = new RestTemplate().postForEntity("http://localhost:8080/register", userRegisterRequest, UserRegisterResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test", response.getBody().getUsername());
        assertEquals("test@gmail.com", response.getBody().getEmail());
    }
}
