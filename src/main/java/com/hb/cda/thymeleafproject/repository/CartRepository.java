package com.hb.cda.thymeleafproject.repository;

import com.hb.cda.thymeleafproject.entity.Cart;
import com.hb.cda.thymeleafproject.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<User> findByUser(User user);
Optional<Cart> findByCartId(String cartId);
Optional<User> findByCartIdAndUserId(String cartId, String userId);

}
