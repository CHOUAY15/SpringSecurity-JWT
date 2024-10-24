package ma.ensa.projet.doctor.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ma.ensa.projet.doctor.api.dto.AuthResponseDTO;
import ma.ensa.projet.doctor.api.dto.DoctorDto;
import ma.ensa.projet.doctor.api.dto.LoginDto;
import ma.ensa.projet.doctor.api.dto.PatientDto;
import ma.ensa.projet.doctor.api.dto.PersonDto;
import ma.ensa.projet.doctor.api.entity.Doctor;
import ma.ensa.projet.doctor.api.entity.Patient;
import ma.ensa.projet.doctor.api.entity.UserEntity;
import ma.ensa.projet.doctor.api.repository.DoctorRepo;
import ma.ensa.projet.doctor.api.repository.PatientRepo;
import ma.ensa.projet.doctor.api.repository.UserRepo;
import ma.ensa.projet.doctor.api.service.interfaces.UserService;
import ma.ensa.projet.doctor.security.JWTGenerator;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DoctorRepo doctorRepository;
    @Autowired
    private PatientRepo patientRepository;

    @Override
    @Transactional
    public AuthResponseDTO authenticateUser(LoginDto loginDto) {
        try {
            // Perform authentication
            Authentication authentication = authenticate(loginDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve the user and generate JWT token
            UserEntity user = getUserByEmail(loginDto.getEmail());
            String token = jwtGenerator.generateToken(authentication);
            
            // Get appropriate PersonDto based on role
            PersonDto personDto = getPersonDto(user);

            // Build and return the authentication response
            return buildAuthResponse(token, personDto,user.getRole());
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationException("User not found") {};
        } catch (Exception e) {
            throw new AuthenticationException("Authentication failed") {};
        }
    }

    @Override
    public Authentication authenticate(LoginDto loginDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public PersonDto getPersonDto(UserEntity user) {
        // Check role and return appropriate DTO
        if (user.getRole() == 0) {  // Patient
            Patient patient = patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
            PatientDto patientDto=new PatientDto();
            patientDto.setFirstName(patient.getFirstName());    
            patientDto.setLastName(patient.getLastName());  
            patientDto.setPhoneNumber(patient.getPhoneNumber());  

            return patientDto;

            
        
        } else if (user.getRole() == 1) {  // Doctor
            Doctor doctor = doctorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
                DoctorDto doctorDto=new DoctorDto();
                doctorDto.setFirstName(doctor.getFirstName());
                doctorDto.setLastName(doctor.getLastName());
                doctorDto.setAddress(doctor.getAddress());
                doctorDto.setBiography(doctor.getBiography());
                doctorDto.setSpecialty(doctor.getSpecialty());
                doctorDto.setYearsExperience(doctor.getYearsExperience());


                return doctorDto;
        }
        
        throw new IllegalStateException("Invalid user role");
    }

    @Override
    public AuthResponseDTO buildAuthResponse(String token, PersonDto personDto,Integer role) {
        return new AuthResponseDTO(
            token,
            "Bearer ",
            personDto,role
        );
    }
}
