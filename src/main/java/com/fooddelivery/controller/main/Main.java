package com.fooddelivery.controller.main;

import com.fooddelivery.model.Users;
import com.fooddelivery.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public class Main {
    @Autowired
    protected UsersRepo usersRepo;
    @Autowired
    protected ReviewsRepo reviewsRepo;
    @Autowired
    protected CategoryRepo categoryRepo;
    @Autowired
    protected RestaurantsRepo restaurantsRepo;
    @Autowired
    protected FoodsRepo foodsRepo;
    @Autowired
    protected OrderingRepo orderingRepo;
    @Value("${upload.img}")
    protected String uploadImg;
    protected String defPhoto = "def_photo.jpg";

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return usersRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }
}