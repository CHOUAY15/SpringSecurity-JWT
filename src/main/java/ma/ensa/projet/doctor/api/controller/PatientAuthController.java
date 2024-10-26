package ma.ensa.projet.doctor.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ma.ensa.projet.doctor.api.dto.PatientRegistrationDto;
import ma.ensa.projet.doctor.api.entity.Patient;
import ma.ensa.projet.doctor.api.entity.UserEntity;
import ma.ensa.projet.doctor.api.repository.PatientRepo;
import ma.ensa.projet.doctor.api.repository.UserRepo;

@RestController
@RequestMapping("/api/auth/patient")

public class PatientAuthController {



    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PatientRegistrationDto patientDto) {

        if (userRepository.existsByEmail(patientDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setEmail(patientDto.getEmail());
        user.setPassword(passwordEncoder.encode(patientDto.getPassword()));
        user.setRole(0); // 0 pour le rôle Patient

        UserEntity savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setUser(savedUser);

        patientRepository.save(patient);

        // // Authentifier l'utilisateur et générer un token JWT
        // Authentication authentication = authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(patientDto.getEmail(), patientDto.getPassword())
        // );

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>("Patient has been registered", HttpStatus.CREATED);    }
}
