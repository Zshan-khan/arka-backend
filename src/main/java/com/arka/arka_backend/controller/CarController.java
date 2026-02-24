package com.arka.arka_backend.controller;

import com.arka.arka_backend.entity.Car;
import com.arka.arka_backend.repository.CarRepository;
import com.arka.arka_backend.service.S3Service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    private final S3Service s3Service;



    // ===============================
    // Upload Car
    // ===============================

    @PostMapping("/upload")
    public Car uploadCar(

            @RequestParam String name,
            @RequestParam String brand,
            @RequestParam double dailyPrice,
            @RequestParam double weeklyPrice,
            @RequestParam double monthlyPrice,
            @RequestParam String description,
            @RequestParam MultipartFile image

    ) throws IOException {

        String imageUrl = s3Service.uploadFile(image);

        Car car = new Car();

        car.setName(name);
        car.setBrand(brand);
        car.setDailyPrice(dailyPrice);
        car.setWeeklyPrice(weeklyPrice);
        car.setMonthlyPrice(monthlyPrice);
        car.setDescription(description);
        car.setImageUrl(imageUrl);

        return carRepository.save(car);

    }



    // ===============================
    // Get All Cars
    // ===============================

    @GetMapping
    public List<Car> getCars() {

        return carRepository.findAll();

    }



    // ===============================
    // Update Car
    // ===============================

    @PutMapping("/{id}")
    public Car updateCar(

            @PathVariable Long id,

            @RequestParam String name,
            @RequestParam String brand,
            @RequestParam double dailyPrice,
            @RequestParam double weeklyPrice,
            @RequestParam double monthlyPrice,
            @RequestParam String description,

            @RequestParam(required = false) MultipartFile image

    ) throws IOException {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));


        car.setName(name);
        car.setBrand(brand);
        car.setDailyPrice(dailyPrice);
        car.setWeeklyPrice(weeklyPrice);
        car.setMonthlyPrice(monthlyPrice);
        car.setDescription(description);


        if (image != null && !image.isEmpty()) {

            try {

                s3Service.deleteFile(car.getImageUrl());

            } catch (Exception e) {

                System.out.println("Old image delete failed, continuing...");

            }

            String newImageUrl = s3Service.uploadFile(image);

            car.setImageUrl(newImageUrl);

        }


        return carRepository.save(car);

    }




    // ===============================
    // Delete Car (DB + S3)
    // ===============================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));


        // Delete image from S3
        try {

            s3Service.deleteFile(car.getImageUrl());

        } catch (Exception e) {

            System.out.println("S3 delete failed, but continuing DB delete");

        }


        // Delete from DB
        carRepository.delete(car);


        return ResponseEntity.ok("Car deleted successfully");

    }

}