package com.hb.cda.thymeleafproject.repository;

import com.hb.cda.thymeleafproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {


}
