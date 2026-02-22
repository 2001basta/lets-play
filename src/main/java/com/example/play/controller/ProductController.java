package com.example.play.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.play.dto.ProductRequest;
import com.example.play.dto.ProductResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productservice;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id){
       return ResponseEntity.ok(productservice.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
        @RequestBody @Valid ProductRequest request
    ){
        return ResponseEntity.ok(productservice.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable String id,
        @RequestBody @Valid ProductRequest request
    ){
        return ResponseEntity.ok(productservice.updateProduct(id,request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id){
        productservice.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
