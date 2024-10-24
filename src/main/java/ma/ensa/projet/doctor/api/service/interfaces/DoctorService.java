package ma.ensa.projet.doctor.api.service.interfaces;

import java.util.List;

import ma.ensa.projet.doctor.api.dto.DoctorDto;

public interface DoctorService {

    List <DoctorDto> getAllDoctors();
    List <DoctorDto> getAllDoctorsBySpeciality(String speciality);

}
