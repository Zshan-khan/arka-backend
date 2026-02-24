package com.arka.arka_backend.repository;

import com.arka.arka_backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}