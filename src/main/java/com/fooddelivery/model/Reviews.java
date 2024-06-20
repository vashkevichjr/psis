package com.fooddelivery.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reviews {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(length = 5000)
    private String text;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Reviews(String text, String date, Users owner) {
        this.text = text;
        this.date = date;
        this.owner = owner;
    }
}
