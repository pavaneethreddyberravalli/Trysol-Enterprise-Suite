package org.trysol.Trysol.Auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @GetMapping("/crm")
    @PreAuthorize("hasRole('SALES')")
    public String crm(){
        return "CRM Module";
    }
}
