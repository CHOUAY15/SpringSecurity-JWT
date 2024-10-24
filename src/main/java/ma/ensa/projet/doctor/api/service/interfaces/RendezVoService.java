package ma.ensa.projet.doctor.api.service.interfaces;

import java.util.List;

import ma.ensa.projet.doctor.api.dto.RendezVousDTO;
import ma.ensa.projet.doctor.api.entity.RendezVous;

public interface RendezVoService {

    RendezVous createRendezVous(RendezVousDTO rendezVousDTO);

    void deleteRendezVous(Integer rendezVousId);

    RendezVous updateRendezVous(Integer rendezVousId, RendezVousDTO rendezVousDTO);

    List<RendezVous> getAllRendezVous();

    List<RendezVous> getRendezVousByPatientId(Integer patId);

    List<RendezVous> getRendezVousByDoctorId(Integer docId);



}