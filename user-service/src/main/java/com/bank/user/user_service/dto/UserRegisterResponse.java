package com.bank.user.user_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponse {
    private Long id;
    private String username;
    private String email;
    private String role;

    @Builder.Default
    private String message="Successfully Registered";
}
