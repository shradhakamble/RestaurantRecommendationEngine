package com.restaurant.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CuisineTracking {
    private Cuisine type;
    private int noOfOrders;
}
