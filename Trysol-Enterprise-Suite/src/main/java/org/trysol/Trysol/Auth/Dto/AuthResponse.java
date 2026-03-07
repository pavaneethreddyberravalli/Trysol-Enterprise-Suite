package org.trysol.Trysol.Auth.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    public String accessToken;
    public String tokenType;
}
