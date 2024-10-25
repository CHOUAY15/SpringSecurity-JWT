package ma.ensa.projet.doctor.api.controller;

import ma.ensa.projet.doctor.api.dto.RendezVousDTO;
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
    public ResponseEntity<RendezVousDTO> createRendezVous(@Valid @RequestBody RendezVousDTO rendezVousDTO) {
        RendezVousDTO newRendezVous = rendezVousService.createRendezVous(rendezVousDTO);
        return new ResponseEntity<>(newRendezVous, HttpStatus.CREATED);
    }
    


    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
        try {
            List<RendezVousDTO> rendezVousList = rendezVousService.getAllRendezVous();
            if (rendezVousList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByPatient(@PathVariable Integer patientId) {
        try {
            List<RendezVousDTO> rendezVousList = rendezVousService.getRendezVousByPatientId(patientId);
            if (rendezVousList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByDoctor(@PathVariable Integer doctorId) {
        try {
            List<RendezVousDTO> rendezVousList = rendezVousService.getRendezVousByDoctorId(doctorId);
            if (rendezVousList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousDTO> updateRendezVous(
            @PathVariable Integer id,
             @RequestBody RendezVousDTO rendezVousDTO) {
     
            RendezVousDTO updatedRendezVous = rendezVousService.updateRendezVous(id, rendezVousDTO);
            return new ResponseEntity<>(updatedRendezVous, HttpStatus.OK);
       
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