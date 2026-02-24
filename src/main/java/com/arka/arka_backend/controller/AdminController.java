package com.arka.arka_backend.controller;

import com.arka.arka_backend.entity.Admin;
import com.arka.arka_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/register")
    public Admin registerAdmin(@RequestBody Admin admin) {

        return adminService.registerAdmin(admin);
    }


    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {

        return adminService.login(admin.getEmail(), admin.getPassword());
    }

}