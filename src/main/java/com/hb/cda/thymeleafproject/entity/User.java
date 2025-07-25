package com.hb.cda.thymeleafproject.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user_table")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private String role;
    @OneToOne(mappedBy = "user")
    private Cart cart;

    public User() {
    }
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

    public User(String id, String username, String password, String role, Cart cart) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.cart = cart;
    }



    public User(String username, String password, String role, Cart cart) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}

