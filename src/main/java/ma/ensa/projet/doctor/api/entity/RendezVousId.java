package ma.ensa.projet.doctor.api.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousId implements Serializable {
    private Integer doctorId;
    private Date date;

   
}
