package com.hb.cda.thymeleafproject.repository;

import com.hb.cda.thymeleafproject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
