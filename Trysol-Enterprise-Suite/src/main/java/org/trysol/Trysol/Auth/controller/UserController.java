package org.trysol.Trysol.Auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trysol.Trysol.Auth.Dto.request.UserRequest;
import org.trysol.Trysol.Auth.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private  final  UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

}


