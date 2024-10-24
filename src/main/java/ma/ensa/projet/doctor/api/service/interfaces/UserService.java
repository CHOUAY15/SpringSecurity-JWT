package ma.ensa.projet.doctor.api.service.interfaces;

import org.springframework.security.core.Authentication;

import ma.ensa.projet.doctor.api.dto.AuthResponseDTO;
import ma.ensa.projet.doctor.api.dto.LoginDto;
import ma.ensa.projet.doctor.api.dto.PersonDto;
import ma.ensa.projet.doctor.api.entity.UserEntity;

public interface UserService {
    AuthResponseDTO authenticateUser(LoginDto loginDto);
    Authentication authenticate(LoginDto loginDto);
    UserEntity getUserByEmail(String email);
    PersonDto getPersonDto(UserEntity user);
    AuthResponseDTO buildAuthResponse(String token, PersonDto personDto,Integer role);
}