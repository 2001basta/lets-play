package com.example.play.service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.play.dto.ProductRequest;
import com.example.play.dto.ProductResponse;
import com.example.play.model.Product;
import com.example.play.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllUsers(){
        return ProductRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    public ProductResponse createProduct(ProductRequest request) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setUserId(email); 
        productRepository.save(product);

        return mapToResponse(product);
    }
    public ProductResponse updateProduct(String id, ProductRequest request) {

        Product product = productRepository.findById(id)
                        .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        validateOwnership(product);

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());

        productRepository.save(product);

        return mapToResponse(product);
    }
    public void deleteProduct(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        validateOwnership(product);

        productRepository.delete(product);
    }

    private void validateOwnership(Product product) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUser = auth.getName();
        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!product.getUserId().equals(currentUser) && !isAdmin) {
            throw new AccessDeniedException(
                    "You are not allowed to modify this product");
        }
    }
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getUserId()
        );
    }
}
