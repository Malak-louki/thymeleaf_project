package com.hb.cda.thymeleafproject.controller;

import com.hb.cda.thymeleafproject.repository.CartRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
private CartRepository repo;
private UserRepository userRepo;

    public CartController(CartRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart",repo.findAll());
        return "cart";
    }
}
