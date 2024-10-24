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

@Service
public class RendezVoServmpl implements RendezVoService {

    @Autowired
    private RendezVoRepos rendezVousRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Autowired
    private DoctorRepo doctorRepository;

    @Override
    public RendezVous createRendezVous(RendezVousDTO rendezVousDTO) {
        // Find patient and doctor
        Patient patient = patientRepository.findById(rendezVousDTO.getPatientId())
            .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        
        Doctor doctor = doctorRepository.findById(rendezVousDTO.getDoctorId())
            .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        // Create new RendezVous
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDate(rendezVousDTO.getDate());
        rendezVous.setPatient(patient);
        rendezVous.setDoctor(doctor);
    

        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public void deleteRendezVous(Integer rendezVousId) {
        if (!rendezVousRepository.existsById(rendezVousId)) {
            throw new EntityNotFoundException("RendezVous not found");
        }
        rendezVousRepository.deleteById(rendezVousId);
    }

    @Override
    public RendezVous updateRendezVous(Integer rendezVousId, RendezVousDTO rendezVousDTO) {
        RendezVous existingRendezVous = rendezVousRepository.findById(rendezVousId)
            .orElseThrow(() -> new EntityNotFoundException("RendezVous not found"));

        // // Find patient and doctor if IDs are different
        // if (!existingRendezVous.getPatient().getId().equals(rendezVousDTO.getPatientId())) {
        //     Patient patient = patientRepository.findById(rendezVousDTO.getPatientId())
        //         .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        //     existingRendezVous.setPatient(patient);
        // }

        // if (!existingRendezVous.getDoctor().getId().equals(rendezVousDTO.getDoctorId())) {
        //     Doctor doctor = doctorRepository.findById(rendezVousDTO.getDoctorId())
        //         .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        //     existingRendezVous.setDoctor(doctor);
        // }

        // Update other fields
        existingRendezVous.setDate(rendezVousDTO.getDate());
        existingRendezVous.setStatus(rendezVousDTO.isStatut());

        return rendezVousRepository.save(existingRendezVous);
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    @Override
    public List<RendezVous> getRendezVousByPatientId(Integer patId) {
        return rendezVousRepository.findByPatientId(patId);
    }

    @Override
    public List<RendezVous> getRendezVousByDoctorId(Integer docId) {
        return rendezVousRepository.findByDoctorId(docId);
    }
}