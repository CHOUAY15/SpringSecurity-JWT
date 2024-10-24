package ma.ensa.projet.doctor.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensa.projet.doctor.api.entity.Doctor;


public interface DoctorRepo extends JpaRepository<Doctor,Integer> {

        Optional<Doctor> findByUserId(Integer userId);

    
}
