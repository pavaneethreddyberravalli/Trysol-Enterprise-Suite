package org.trysol.Trysol.Auth.Dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
//    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit"
//    )
    private String password;
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
    private String role;

}







