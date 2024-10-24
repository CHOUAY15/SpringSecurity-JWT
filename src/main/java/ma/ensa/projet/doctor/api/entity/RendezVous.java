package ma.ensa.projet.doctor.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "rendezvous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Temporal(TemporalType.TIMESTAMP)  
    @Column(name = "date_rendezvous", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    

    @Column(name = "status", nullable = false)
    private boolean status=false;  
}
