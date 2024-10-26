package ma.ensa.projet.doctor.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@IdClass(RendezVousId.class)
@Table(name = "rendezvous")
public class RendezVous {

    @Id
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId; 

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_rendezvous", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "status", nullable = false)
    private boolean status = false;
}