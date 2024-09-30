package com.example.e_commerce.entities;

import com.example.e_commerce.dto.UserDto;
import com.example.e_commerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    private byte[] img;

    public UserDto mapUserToUserDto() {
        return new UserDto(id, email, name, userRole);
    }
}
