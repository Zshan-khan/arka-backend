package com.arka.arka_backend.repository;

import com.arka.arka_backend.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}