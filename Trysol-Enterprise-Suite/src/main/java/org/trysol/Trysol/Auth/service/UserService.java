package org.trysol.Trysol.Auth.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.trysol.Trysol.Auth.Dto.UserRequest;
import org.trysol.Trysol.Auth.Repository.RoleRepository;
import org.trysol.Trysol.Auth.Repository.UserRepository;
import org.trysol.Trysol.Auth.entity.Role;
import org.trysol.Trysol.Auth.entity.User;
import org.trysol.Trysol.Auth.exception.PasswordMismatchException;
import org.trysol.Trysol.Auth.exception.RoleNotFoundException;
import org.trysol.Trysol.Auth.exception.UsernameAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class UserService {

    private  final UserRepository userRepository;
    private  final RoleRepository rolerepository;
    private final PasswordEncoder passwordEncoder;


    public String createUser(UserRequest request){
        if((userRepository.existsByUsername(request.getUsername()))) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        String roleName =  request.getRoleName().toUpperCase();

        Role role = rolerepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

         userRepository.save(user);

        return "User created successfully";
    }
}
