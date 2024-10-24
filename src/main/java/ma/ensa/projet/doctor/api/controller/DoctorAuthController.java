package ma.ensa.projet.doctor.api.controller;

import ma.ensa.projet.doctor.api.entity.Doctor;
import ma.ensa.projet.doctor.api.entity.UserEntity;
import ma.ensa.projet.doctor.api.repository.DoctorRepo;
import ma.ensa.projet.doctor.api.repository.UserRepo;
import ma.ensa.projet.doctor.api.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth/doctor")
@CrossOrigin(origins = "*")
public class DoctorAuthController {

 
    
    @Autowired
    private UserRepo userRepository;
    
    @Autowired
    private DoctorRepo doctorRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("specialty") String specialty,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("address") String address,
            @RequestParam("yearsExperience") int yearsExperience,
            @RequestParam("biography") String biography) {

        if (userRepository.existsByEmail(email)) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // Create new user
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(1); // 1 for Doctor role
        
        UserEntity savedUser = userRepository.save(user);

        // Create doctor profile
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setSpecialty(specialty);
        doctor.setAddress(address);
        doctor.setYearsExperience(yearsExperience);
        doctor.setBiography(biography);
        doctor.setUser(savedUser);

        // Handle image upload
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.storeFile(image);
            doctor.setImage(fileName);
        }

        doctorRepository.save(doctor);

        // // Authenticate the user and generate token
        // Authentication authentication = authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(email, password)
        // );

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>("Doctor has been registered", HttpStatus.CREATED);    }
}