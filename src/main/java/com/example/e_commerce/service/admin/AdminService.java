package com.example.e_commerce.service.admin;

import com.example.e_commerce.dto.CategoryDto;
import com.example.e_commerce.dto.OrderDto;
import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.entities.CategoryEntity;
import com.example.e_commerce.entities.ProductEntity;
import java.io.IOException;
import java.util.List;

public interface AdminService {
    CategoryEntity createdCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    ProductEntity postProduct(Long categoryId, ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long categoryId, Long productId, ProductDto productDto) throws IOException;

    List<OrderDto> getAllOrders();
}
