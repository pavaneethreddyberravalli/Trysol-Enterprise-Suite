package org.trysol.Trysol.Auth.Dto.request;

import lombok.Data;

@Data
    public class ResetPassword {

        private String token;
        private String newPassword;
        private String confirmPassword;

    }

