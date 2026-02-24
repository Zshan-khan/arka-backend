package com.arka.arka_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;
}