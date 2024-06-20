package com.fooddelivery.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Restaurants {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String fio;
    private String photo;
    private String address;
    private String tel;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Foods> foods;

    public Restaurants(String photo) {
        this.fio = "Название";
        this.photo = photo;
        this.address = "";
        this.tel = "";
    }
}
