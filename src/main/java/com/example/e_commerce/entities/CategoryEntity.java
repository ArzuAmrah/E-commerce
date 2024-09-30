package com.example.e_commerce.entities;


import com.example.e_commerce.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    public CategoryDto getCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        return categoryDto;
    }
}
