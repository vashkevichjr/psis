package com.fooddelivery.model;

import com.fooddelivery.model.enums.StatusOrdering;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Foods implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private int count = 0;
    private String photo;
    private int price;
    private int weight;
    private String description;

    @ManyToOne
    private Category category;
    @ManyToOne
    private Restaurants restaurant;
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Ordering> orderings;

    public Foods(String name, String photo, int price, String description, Category category, Restaurants restaurant, int weight) {
        this.name = name;
        this.category = category;
        this.restaurant = restaurant;
        this.photo = photo;
        this.price = price;
        this.description = description;
        this.weight = weight;
    }

    public int getIncome() {
        return orderings.stream().reduce(0, (i, ordering) -> {
            if (ordering.getStatusOrdering() == StatusOrdering.DONE) {
                return i + ordering.getPrice();
            }
            return i;
        }, Integer::sum);
    }

    public int getCount() {
        return orderings.stream().reduce(0, (i, ordering) -> {
            if (ordering.getStatusOrdering() == StatusOrdering.DONE) {
                return i + ordering.getCount();
            }
            return i;
        }, Integer::sum);
    }

}
