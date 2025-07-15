package com.hb.cda.thymeleafproject.security;

import com.hb.cda.thymeleafproject.entity.Cart;
import com.hb.cda.thymeleafproject.entity.CartItem;
import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.CartRepository;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartService {
private final CartRepository cartRepo;
private final ProductRepository productRepo;
private final UserRepository userRepo;

    public CartService(CartRepository cartRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public Cart getCart(User user) {
        return cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart(user);
                    cartRepo.save(cart);
                    return cart;
                });
    }

    public void  addProductToCart(User user, String product_id){

        Product product = productRepo.findById(product_id).orElseThrow(()-> new IllegalArgumentException("Product not found : " + product_id));

        //si le produit est déjà dans le panier
        Cart cart = getCart(user);
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item ->item.getProduct().equals(product))
                .findFirst();

        if(existingItem.isPresent()){
            existingItem.get().setQuantity(existingItem.get().getQuantity()+1);
        }else{
            CartItem newItem = new CartItem(1, product);
            newItem.setCart(cart);
            cart.getCartItems().add(newItem);
        }

        cartRepo.save(cart);
    }
    public void removeProductFromCart(User user, String productId) {
        Cart cart = getCart(user);
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepo.save(cart);
    }

    public void clearCart(User user) {
        Cart cart = getCart(user);
        cart.getCartItems().clear();
        cartRepo.save(cart);
    }

    public Double getTotalPrice(User user) {
        Cart cart = getCart(user);

        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
