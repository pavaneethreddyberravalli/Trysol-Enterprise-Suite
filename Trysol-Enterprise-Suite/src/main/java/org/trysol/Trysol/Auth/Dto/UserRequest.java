package org.trysol.Trysol.Auth.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String roleName;
    private String confirmPassword;
}
