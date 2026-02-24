package com.arka.arka_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private Double dailyPrice;

    private Double weeklyPrice;

    private Double monthlyPrice;

    private String imageUrl;

    private String description;

}