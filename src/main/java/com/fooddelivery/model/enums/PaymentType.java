package com.fooddelivery.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {
    OFFLINE("Наличными"),
    CARD_OFFLINE("Картой при получении"),
    CARD_ONLINE("Оплата онлайн"),
    ;

    private final String name;
}
