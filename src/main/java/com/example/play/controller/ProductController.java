package com.example.play.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.example.play.dto.Response;
import com.example.play.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductResponse>> getProductById(@PathVariable String id){
       return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Response<ProductResponse>> createProduct(
        @RequestBody @Valid ProductRequest request,
        @AuthenticationPrincipal UserDetails user
    ){
        return productService.createProduct(request, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductResponse>> updateProduct(
        @PathVariable String id,
        @RequestBody @Valid ProductRequest request,
        @AuthenticationPrincipal UserDetails user
    ){
        return productService.updateProduct(id,request,user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteProduct(@PathVariable String id,@AuthenticationPrincipal UserDetails user){
        return productService.deleteProduct(id,user);
    }
}
