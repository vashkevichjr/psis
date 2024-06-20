package com.fooddelivery.controller;

import com.fooddelivery.controller.main.Attributes;
import com.fooddelivery.model.Restaurants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/restaurant")
public class RestaurantCont extends Attributes {
    @GetMapping
    public String Restaurant(Model model) {
        AddAttributesRestaurant(model);
        return "restaurant";
    }

    @PostMapping("/edit/photo")
    public String RestaurantEditPhoto(Model model, @RequestParam MultipartFile photo) {
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "restaurants/" + uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesRestaurant(model);
                return "restaurant";
            }
            Restaurants restaurant = getUser().getRestaurant();
            restaurant.setPhoto(res);
            restaurantsRepo.save(restaurant);
        }
        return "redirect:/restaurant";
    }

    @PostMapping("/edit")
    public String RestaurantEdit(@RequestParam String fio, @RequestParam String tel, @RequestParam String address) {
        Restaurants restaurant = getUser().getRestaurant();
        restaurant.setFio(fio);
        restaurant.setTel(tel);
        restaurant.setAddress(address);
        restaurantsRepo.save(restaurant);
        return "redirect:/restaurant";
    }
}
