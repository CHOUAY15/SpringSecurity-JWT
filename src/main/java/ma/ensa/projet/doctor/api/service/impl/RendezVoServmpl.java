package ma.ensa.projet.doctor.api.service.impl;

import ma.ensa.projet.doctor.api.dto.RendezVousDTO;
import ma.ensa.projet.doctor.api.entity.Doctor;
import ma.ensa.projet.doctor.api.entity.Patient;
import ma.ensa.projet.doctor.api.entity.RendezVous;
import ma.ensa.projet.doctor.api.entity.RendezVousId; 
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
        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(rendezVousDTO.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        // Set the doctorId explicitly since it's part of the composite key
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDoctorId(doctor.getId()); // Add this line
        rendezVous.setDoctor(doctor);
        rendezVous.setPatient(patient);
        rendezVous.setDate(rendezVousDTO.getDate());
        rendezVous.setStatus(rendezVousDTO.isStatut());

        return convertToDto(rendezVousRepository.save(rendezVous));
    }


    @Override
    public void deleteRendezVous(RendezVousId rendezVousId) { // Change parameter to composite key type
        if (!rendezVousRepository.existsById(rendezVousId)) {
            throw new EntityNotFoundException("RendezVous not found");
        }
        rendezVousRepository.deleteById(rendezVousId);
    }

    @Override
    public RendezVousDTO updateRendezVous(RendezVousId rendezVousId, RendezVousDTO rendezVousDTO) {
        RendezVous existingRendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new EntityNotFoundException("RendezVous not found"));

        // Don't modify doctorId as it's part of the primary key
        if (!rendezVousDTO.getPatientId().equals(existingRendezVous.getPatient().getId())) {
            Patient newPatient = patientRepository.findById(rendezVousDTO.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
            existingRendezVous.setPatient(newPatient);
        }

        existingRendezVous.setStatus(rendezVousDTO.isStatut());
        
        // Date is part of the primary key, so it shouldn't be modified
        // Remove the date modification logic

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
        dto.setDoctorName(rendezVous.getDoctor().getLastName() + " " + rendezVous.getDoctor().getFirstName());
        dto.setPtienName(rendezVous.getPatient().getLastName() + " " + rendezVous.getPatient().getFirstName());
        dto.setDate(rendezVous.getDate());
        dto.setPatientId(rendezVous.getPatient().getId());
        dto.setDoctorId(rendezVous.getDoctor().getId());
        dto.setStatut(rendezVous.isStatus());
        return dto;
    }
}
