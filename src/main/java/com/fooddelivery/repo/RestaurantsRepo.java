package com.fooddelivery.repo;

import com.fooddelivery.model.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsRepo extends JpaRepository<Restaurants, Long> {
}
