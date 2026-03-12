package org.trysol.Trysol.Auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    @GetMapping("/reports")
    @PreAuthorize("hasRole('MANAGER')")
    public String reports(){
        return "Manager Reports";
    }
}
