package org.trysol.Trysol.Auth.Dto.request;

import lombok.Data;

@Data
public class ForgotPassword {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

