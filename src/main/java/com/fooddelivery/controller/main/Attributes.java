package com.fooddelivery.controller.main;

import com.fooddelivery.model.Category;
import com.fooddelivery.model.Foods;
import com.fooddelivery.model.Ordering;
import com.fooddelivery.model.Users;
import com.fooddelivery.model.enums.PaymentType;
import com.fooddelivery.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", usersRepo.findAll());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesReviews(Model model) {
        AddAttributes(model);
        model.addAttribute("reviews", reviewsRepo.findAll());
    }

    protected void AddAttributesFoodAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("restaurants", restaurantsRepo.findAll());
    }

    protected void AddAttributesFoodEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("food", foodsRepo.getReferenceById(id));
        model.addAttribute("restaurants", restaurantsRepo.findAll());
    }

    protected void AddAttributesFoodsMy(Model model) {
        AddAttributes(model);
        Users user = getUser();
        if (user.getRole() == Role.MANAGER) {
            List<Ordering> orderings = new ArrayList<>();
            for (Foods i : user.getRestaurant().getFoods()) {
                orderings.addAll(i.getOrderings());
            }
            model.addAttribute("orderings", orderings);
        } else {
            model.addAttribute("orderings", user.getOrderings());
        }
    }

    protected void AddAttributesFood(Model model, Long id) {
        AddAttributes(model);
        Foods food = foodsRepo.getReferenceById(id);
        model.addAttribute("food", food);
        model.addAttribute("paymentTypes", PaymentType.values());
    }

    protected void AddAttributesIndex(Model model) {
        AddAttributes(model);
        model.addAttribute("foods", foodsRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
    }

    protected void AddAttributesCategory(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
    }

    protected void AddAttributesRestaurant(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", categoryRepo.findAll());
    }

    protected void AddAttributesRestaurants(Model model) {
        AddAttributes(model);
        model.addAttribute("restaurants", restaurantsRepo.findAll());
    }

    protected void AddAttributesSearch(Model model, String name, Long categoryId) {
        AddAttributes(model);
        Category category = categoryRepo.getReferenceById(categoryId);
        model.addAttribute("foods", foodsRepo.findAllByNameContainingAndCategory_Name(name, category.getName()));
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("selectedCId", categoryId);
        model.addAttribute("name", name);
    }

    protected void AddAttributesStats(Model model) {
        AddAttributes(model);
        model.addAttribute("foods", foodsRepo.findAll());
    }
}
