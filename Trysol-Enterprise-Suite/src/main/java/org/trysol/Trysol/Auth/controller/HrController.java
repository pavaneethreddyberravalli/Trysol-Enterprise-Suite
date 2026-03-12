package org.trysol.Trysol.Auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hr")
public class HrController {


    @GetMapping("/hr")
    @PreAuthorize("has Role/(HR)")
    public String enquiry(){
        return "Hr enquiry data";
    }

    @GetMapping("/pip")
    @PreAuthorize("hasRole('HR')")
    public String pip(){
        return "PIP Management";
    }

    @GetMapping("/offer")
    @PreAuthorize("hasRole('HR')")
    public String offer(){
        return "Offer Letter Module";
    }








}
