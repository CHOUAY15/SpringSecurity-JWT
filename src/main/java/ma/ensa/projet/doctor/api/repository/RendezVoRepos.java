package ma.ensa.projet.doctor.api.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensa.projet.doctor.api.entity.RendezVous;

@Repository
public interface RendezVoRepos extends JpaRepository<RendezVous, Integer> {
    
    List<RendezVous> findByPatientId(Integer patientId);
    List<RendezVous> findByDoctorId(Integer doctorId);

    
}
