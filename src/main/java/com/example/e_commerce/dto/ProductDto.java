package com.example.e_commerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    @NotNull(message = "Product ID cannot be null")
    @Min(value = 1, message = "Product ID must be a positive number")
    private Long id;

    @NotEmpty(message = "Product name cannot be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Product description cannot be empty")
    @Size(max = 500, message = "Product description must be less than or equal to 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Integer price;

    private MultipartFile image;

    private byte[] returnedImage;

    @NotNull(message = "Category ID cannot be null")
    @Min(value = 1, message = "Category ID must be a positive number")
    private Long categoryId;

    @NotEmpty(message = "Category name cannot be empty")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String categoryName;

}
