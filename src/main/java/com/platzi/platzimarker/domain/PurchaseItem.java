package com.platzi.platzimarker.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseItem {
    private Long productoId;
    private int quantity;
    private Double total;
    private boolean active;
}
