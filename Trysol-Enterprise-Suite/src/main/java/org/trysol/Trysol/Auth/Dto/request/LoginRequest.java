package org.trysol.Trysol.Auth.Dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
