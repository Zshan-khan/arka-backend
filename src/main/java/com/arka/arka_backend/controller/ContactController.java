package com.arka.arka_backend.controller;

import com.arka.arka_backend.entity.Contact;
import com.arka.arka_backend.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    @PostMapping
    public Contact submit(@RequestBody Contact contact) {

        return contactService.save(contact);

    }


    // optional — admin can view all leads
    @GetMapping
    public java.util.List<Contact> getAll(){

        return contactService.getAll();

    }

}