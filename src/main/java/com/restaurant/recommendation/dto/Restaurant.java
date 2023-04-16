package com.restaurant.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class Restaurant {
    private String restaurantId;
    private Cuisine cuisine;
    private int costBracket;
    private float rating;
    private boolean isRecommended;
    private Date onboardedTime;
}
