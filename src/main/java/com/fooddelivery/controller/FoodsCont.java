package com.fooddelivery.controller;

import com.fooddelivery.controller.main.Attributes;
import com.fooddelivery.model.Foods;
import com.fooddelivery.model.Ordering;
import com.fooddelivery.model.Users;
import com.fooddelivery.model.enums.PaymentType;
import com.fooddelivery.model.enums.StatusOrdering;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/foods")
public class FoodsCont extends Attributes {

    @GetMapping("/add")
    public String foodAdd(Model model) {
        AddAttributesFoodAdd(model);
        return "foodAdd";
    }

    @GetMapping("/my")
    public String orderingsMy(Model model) {
        AddAttributesFoodsMy(model);
        return "foodsMy";
    }

    @GetMapping("/notconf/{id}")
    public String orderingNotConf(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setStatusOrdering(StatusOrdering.NOT_CONF);
        orderingRepo.save(ordering);
        return "redirect:/foods/my";
    }

    @GetMapping("/conf/{id}")
    public String orderingConf(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setStatusOrdering(StatusOrdering.CONF);
        orderingRepo.save(ordering);
        return "redirect:/foods/my";
    }

    @GetMapping("/done/{id}")
    public String orderingDone(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setStatusOrdering(StatusOrdering.DONE);
        ordering.getFood().setCount(ordering.getFood().getCount() + 1);
        orderingRepo.save(ordering);
        return "redirect:/foods/my";
    }

    @GetMapping("/{id}")
    public String food(Model model, @PathVariable Long id) {
        AddAttributesFood(model, id);
        return "food";
    }

    @GetMapping("/edit/{id}")
    public String foodEdit(Model model, @PathVariable Long id) {
        AddAttributesFoodEdit(model, id);
        return "foodEdit";
    }

    @GetMapping("/delete/{id}")
    public String foodDelete(@PathVariable Long id) {
        List<Ordering> orderings = orderingRepo.findAllByFood_Id(id);
        for (Ordering i : orderings) {
            orderingRepo.deleteById(i.getId());
        }
        foodsRepo.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/ordering/{foodId}")
    public String foodOrdering(@RequestParam String date, @RequestParam int count, @RequestParam PaymentType paymentType, @RequestParam String time, @PathVariable Long foodId) {
        Users client = getUser();
        Foods food = foodsRepo.getReferenceById(foodId);

        Ordering ordering = new Ordering(client, food, date, time, paymentType, count);

        orderingRepo.save(ordering);
        return "redirect:/foods/{foodId}";
    }

    @PostMapping("/add")
    public String foodAddNew(Model model, @RequestParam String name, @RequestParam int weight, @RequestParam Long categoryId, @RequestParam MultipartFile photo, @RequestParam int price, @RequestParam String description) {
        String res = "";
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "foods/" + uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesFoodAdd(model);
                return "foodAdd";
            }
        }

        foodsRepo.save(new Foods(name, res, price, description, categoryRepo.getReferenceById(categoryId), getUser().getRestaurant(), weight));

        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String foodEditOld(Model model, @RequestParam String name, @RequestParam int weight, @RequestParam Long categoryId, @RequestParam MultipartFile photo, @RequestParam int price, @RequestParam String description, @PathVariable Long id) {
        Foods food = foodsRepo.getReferenceById(id);
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "foods/" + uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesFoodEdit(model, id);
                return "foodEdit";
            }
            food.setPhoto(res);
        }

        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);
        food.setWeight(weight);
        food.setCategory(categoryRepo.getReferenceById(categoryId));

        foodsRepo.save(food);

        return "redirect:/";
    }
}
