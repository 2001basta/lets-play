package com.example.play.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.play.dto.ProductRequest;
import com.example.play.dto.ProductResponse;
import com.example.play.dto.Response;
import com.example.play.model.Product;
import com.example.play.model.User;
import com.example.play.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<Response<List<ProductResponse>>> getAllUsers() {
        return ResponseEntity.ok(Response.success(
                productRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .toList(),
                "successfully"));
    }

    public ResponseEntity<Response<ProductResponse>> getProductById(String id) {
        return productRepository.findById(id)
                .map((product) -> ResponseEntity.ok(Response.success(mapToResponse(product), "successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(null, "user not found")));

    }

    public ResponseEntity<Response<ProductResponse>> createProduct(ProductRequest request, UserDetails auth) {

        String userId = ((User) auth).getId();

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setUserId(userId);
        productRepository.save(product);

        return ResponseEntity.ok(Response.success(mapToResponse(product), "Successfuly"));
    }

    public ResponseEntity<Response<ProductResponse>> updateProduct(
            String id,
            ProductRequest request,
            UserDetails auth) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(null, "Product not found"));
        }

        Product product = optionalProduct.get();

        // ownership check
        if (!isOwnerOrAdmin(product, auth)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Response.error(null, "You are not allowed to modify this product"));
        }

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());

        productRepository.save(product);

        return ResponseEntity.ok(
                Response.success(mapToResponse(product), "Updated successfully"));
    }



    public ResponseEntity<Response<Void>> deleteProduct(
            String id,
            UserDetails auth) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(null, "Product not found"));
        }

        Product product = optionalProduct.get();

        if (!isOwnerOrAdmin(product, auth)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Response.error(null, "You are not allowed to delete this product"));
        }

        productRepository.delete(product);

        return ResponseEntity.ok(
                Response.success(null, "Deleted successfully"));
    }

    private boolean isOwnerOrAdmin(Product product, UserDetails auth) {

        String currentUserId = ((User) auth).getId();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .map(a -> a.getAuthority().replaceFirst("^ROLE_", ""))
                .anyMatch(role -> role.equalsIgnoreCase("ADMIN"));
        return product.getUserId().equals(currentUserId) || isAdmin;
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getUserId());
    }
}
