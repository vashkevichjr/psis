package com.fooddelivery.repo;

import com.fooddelivery.model.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodsRepo extends JpaRepository<Foods, Long> {
    List<Foods> findAllByNameContainingAndCategory_Name(String name, String categoryName);
}
