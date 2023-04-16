package com.restaurant.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CostTracking {
    private int type;
    private int noOfOrders;
}
