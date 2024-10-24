package ma.ensa.projet.doctor.api.service.impl;

import ma.ensa.projet.doctor.api.dto.RendezVousDTO;
import ma.ensa.projet.doctor.api.entity.Doctor;
import ma.ensa.projet.doctor.api.entity.Patient;
import ma.ensa.projet.doctor.api.entity.RendezVous;
import ma.ensa.projet.doctor.api.repository.DoctorRepo;
import ma.ensa.projet.doctor.api.repository.PatientRepo;
import ma.ensa.projet.doctor.api.repository.RendezVoRepos;
import ma.ensa.projet.doctor.api.service.interfaces.RendezVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendezVoServmpl implements RendezVoService {

    @Autowired
    private RendezVoRepos rendezVousRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Autowired
    private DoctorRepo doctorRepository;

    @Override
    public RendezVousDTO createRendezVous(RendezVousDTO rendezVousDTO) {
        // Find patient and doctor
        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId())
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    
        Doctor doctor = doctorRepository.findById(rendezVousDTO.getDoctorId())
            .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
    
        // Check if a RendezVous with the same date already exists
        boolean exists = rendezVousRepository.existsByDate(rendezVousDTO.getDate());
        if (exists) {
            throw new IllegalArgumentException("Impossible de créer RendezVous : la date existe déjà");
        }
    
        // Create new RendezVous
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDate(rendezVousDTO.getDate());
        rendezVous.setPatient(patient);
        rendezVous.setDoctor(doctor);
    
        // Save and convert to DTO
        return convertToDto(rendezVousRepository.save(rendezVous));
    }
    

    @Override
    public void deleteRendezVous(Integer rendezVousId) {
        if (!rendezVousRepository.existsById(rendezVousId)) {
            throw new EntityNotFoundException("RendezVous not found");
        }
        rendezVousRepository.deleteById(rendezVousId);
    }

    @Override
    public RendezVousDTO updateRendezVous(Integer rendezVousId, RendezVousDTO rendezVousDTO) {
        RendezVous existingRendezVous = rendezVousRepository.findById(rendezVousId)
            .orElseThrow(() -> new EntityNotFoundException("RendezVous not found"));

        // Update other fields
        existingRendezVous.setDate(rendezVousDTO.getDate());
        existingRendezVous.setStatus(rendezVousDTO.isStatut());

        // Save and convert to DTO
        return convertToDto(rendezVousRepository.save(existingRendezVous));
    }

    @Override
    public List<RendezVousDTO> getAllRendezVous() {
        return rendezVousRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<RendezVousDTO> getRendezVousByPatientId(Integer patId) {
        return rendezVousRepository.findByPatientId(patId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<RendezVousDTO> getRendezVousByDoctorId(Integer docId) {
        return rendezVousRepository.findByDoctorId(docId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Convert entity to DTO
    private RendezVousDTO convertToDto(RendezVous rendezVous) {
        RendezVousDTO dto = new RendezVousDTO();
        dto.setDoctorName(rendezVous.getDoctor().getLastName()+" "+rendezVous.getDoctor().getFirstName());
        dto.setPtienName(rendezVous.getPatient().getLastName()+" "+rendezVous.getPatient().getFirstName());
        dto.setDate(rendezVous.getDate());
        dto.setPatientId(rendezVous.getPatient().getId());
        dto.setDoctorId(rendezVous.getDoctor().getId());
        dto.setStatut(rendezVous.isStatus());
        return dto;
    }
}
