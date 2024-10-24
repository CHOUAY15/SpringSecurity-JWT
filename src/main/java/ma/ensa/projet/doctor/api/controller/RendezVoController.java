package ma.ensa.projet.doctor.api.controller;

import ma.ensa.projet.doctor.api.dto.RendezVousDTO;
import ma.ensa.projet.doctor.api.entity.RendezVous;
import ma.ensa.projet.doctor.api.service.interfaces.RendezVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rendv")

public class RendezVoController {

    @Autowired
    private RendezVoService rendezVousService;

    @PostMapping
    public ResponseEntity<RendezVous> createRendezVous(@Valid @RequestBody RendezVousDTO rendezVousDTO) {
        try {
            RendezVous newRendezVous = rendezVousService.createRendezVous(rendezVousDTO);
            return new ResponseEntity<>(newRendezVous, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<RendezVous>> getAllRendezVous() {
        try {
            List<RendezVous> rendezVousList = rendezVousService.getAllRendezVous();
            if (rendezVousList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RendezVous>> getRendezVousByPatient(@PathVariable Integer patientId) {
        try {
            List<RendezVous> rendezVousList = rendezVousService.getRendezVousByPatientId(patientId);
            if (rendezVousList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<RendezVous>> getRendezVousByDoctor(@PathVariable Integer doctorId) {
        try {
            List<RendezVous> rendezVousList = rendezVousService.getRendezVousByDoctorId(doctorId);
            if (rendezVousList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVous> updateRendezVous(
            @PathVariable Integer id,
            @Valid @RequestBody RendezVousDTO rendezVousDTO) {
        try {
            RendezVous updatedRendezVous = rendezVousService.updateRendezVous(id, rendezVousDTO);
            return new ResponseEntity<>(updatedRendezVous, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRendezVous(@PathVariable Integer id) {
        try {
            rendezVousService.deleteRendezVous(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}