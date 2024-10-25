package ma.ensa.projet.doctor.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RendezVousDTO {
    private Integer id;

    @NotNull(message = "Date cannot be null")
    
    private Date date;

    @NotNull(message = "Patient ID cannot be null")
    private Integer patientId;

    @NotNull(message = "Doctor ID cannot be null")
    private Integer doctorId;

    private boolean statut;
    private String ptienName;
    private String doctorName;



 
}
