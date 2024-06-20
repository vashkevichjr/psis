package com.fooddelivery.model;

import com.fooddelivery.model.enums.PaymentType;
import com.fooddelivery.model.enums.StatusOrdering;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ordering {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String date;
    private String time;
    private int count;

    @Enumerated(EnumType.STRING)
    private StatusOrdering statusOrdering = StatusOrdering.WAITING;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    private Users client;
    @ManyToOne
    private Foods food;

    public Ordering(Users client, Foods food, String date, String time, PaymentType paymentType, int count) {
        this.client = client;
        this.food = food;
        this.date = date;
        this.time = time;
        this.paymentType = paymentType;
        this.count = count;
    }

    public int getPrice() {
        return food.getPrice() * count;
    }
}
