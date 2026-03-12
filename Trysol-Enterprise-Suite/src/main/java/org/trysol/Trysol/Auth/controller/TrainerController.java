package org.trysol.Trysol.Auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {
    @GetMapping("/training")
    @PreAuthorize("hasRole('TRAINER')")
    public String training(){
        return "L&D Training Module";
    }
}
