package com.arka.arka_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_messages")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String name;


    private String email;


    private String phone;


    @Column(length = 2000)
    private String message;

}