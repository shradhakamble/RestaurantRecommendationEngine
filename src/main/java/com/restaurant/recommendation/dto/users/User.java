package com.restaurant.recommendation.dto.users;

import com.restaurant.recommendation.dto.CostTracking;
import com.restaurant.recommendation.dto.CuisineTracking;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private CuisineTracking[]  cuisines;
    private CostTracking[] costBracket;
}
