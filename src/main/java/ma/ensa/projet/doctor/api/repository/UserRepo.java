package ma.ensa.projet.doctor.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensa.projet.doctor.api.entity.UserEntity;

public interface UserRepo  extends  JpaRepository<UserEntity,Integer>{

    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    
}
