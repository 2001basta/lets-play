package com.example.play.repository;

import java.util.List;

import com.example.play.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;  

public interface ProductRepository  extends MongoRepository<Product,String> {
    List<Product> findByUserId(String userId);
}