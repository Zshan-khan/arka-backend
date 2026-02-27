    package com.arka.arka_backend.service;

    import com.arka.arka_backend.entity.Admin;
    import com.arka.arka_backend.repository.AdminRepository;
    import com.arka.arka_backend.security.JwtService;

    import lombok.RequiredArgsConstructor;

    import org.springframework.security.crypto.password.PasswordEncoder;

    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class AdminService {


        private final AdminRepository adminRepository;

        private final PasswordEncoder passwordEncoder;

        private final JwtService jwtService;



        public Admin registerAdmin(Admin admin) {


            admin.setPassword(
                    passwordEncoder.encode(admin.getPassword())
            );


            if (admin.getRole() == null) {

                admin.setRole("ADMIN");

            }


            return adminRepository.save(admin);

        }



        public String login(String email, String password) {


            Admin admin = adminRepository
                    .findByEmail(email)
                    .orElseThrow();


            if (!passwordEncoder.matches(password, admin.getPassword())) {

                throw new RuntimeException("Invalid password");

            }


            return jwtService.generateToken(email);

        }

    }