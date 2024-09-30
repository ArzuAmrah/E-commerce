package com.example.e_commerce.entities;

import com.example.e_commerce.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    private Integer price;

//    @Column(columnDefinition = "longlob")
    @Column(columnDefinition = "BYTEA")
    private byte[] image;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CategoryEntity category;

    public ProductDto getProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setReturnedImage(image);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;

    }

}
