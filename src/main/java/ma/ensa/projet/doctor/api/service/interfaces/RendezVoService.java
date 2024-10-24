package ma.ensa.projet.doctor.api.service.interfaces;

import java.util.List;

import ma.ensa.projet.doctor.api.dto.RendezVousDTO;
import ma.ensa.projet.doctor.api.entity.RendezVous;

public interface RendezVoService {

    RendezVousDTO createRendezVous(RendezVousDTO rendezVousDTO);

    void deleteRendezVous(Integer rendezVousId);

    RendezVousDTO updateRendezVous(Integer rendezVousId, RendezVousDTO rendezVousDTO);

    List<RendezVousDTO> getAllRendezVous();

    List<RendezVousDTO> getRendezVousByPatientId(Integer patId);

    List<RendezVousDTO> getRendezVousByDoctorId(Integer docId);



}