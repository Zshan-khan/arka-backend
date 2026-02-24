package com.arka.arka_backend.service;

import com.arka.arka_backend.entity.Contact;
import com.arka.arka_backend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;


    public Contact save(Contact contact){

        return contactRepository.save(contact);

    }


    public List<Contact> getAll(){

        return contactRepository.findAll();

    }

}