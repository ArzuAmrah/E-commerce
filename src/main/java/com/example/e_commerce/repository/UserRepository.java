package com.example.e_commerce.repository;

import com.example.e_commerce.entities.UserEntity;
import com.example.e_commerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findFirstByEmail(String email);

    Optional<UserEntity> findByUserRole(UserRole userRole);
}
