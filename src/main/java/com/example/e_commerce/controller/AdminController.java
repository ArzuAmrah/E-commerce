package com.example.e_commerce.controller;

import com.example.e_commerce.dto.CategoryDto;
import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.entities.CategoryEntity;
import com.example.e_commerce.entities.ProductEntity;
import com.example.e_commerce.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private AdminService adminService;

    @PostMapping("/category")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryEntity createdCategory = adminService.createdCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = adminService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @PostMapping("/product/{categoryId}")
    public ResponseEntity<ProductEntity> postProduct(@PathVariable Long categoryId, @ModelAttribute ProductDto productDto) throws IOException {
        ProductEntity postedProduct = adminService.postProduct(categoryId, productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(postedProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtoList = adminService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @DeleteMapping("/product/{id}")
    private ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id) {
        ProductDto productDto = adminService.getProductById(id);
        if(productDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long categoryId,
            @PathVariable Long productId,
            @ModelAttribute ProductDto productDto) throws IOException {
        ProductDto updateProduct = adminService.updateProduct(categoryId,productId, productDto);
        if(updateProduct == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.ok(updateProduct);
    }
}
