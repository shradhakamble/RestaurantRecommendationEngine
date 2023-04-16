package com.restaurant.recommendation.dto.users;

import com.restaurant.recommendation.dto.CostTracking;
import com.restaurant.recommendation.dto.Cuisine;
import com.restaurant.recommendation.dto.CuisineTracking;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class UserWrapper extends User {

    private Cuisine primaryCuisine;
    private List<Cuisine> secondaryCuisines;
    private int primaryCostBracket;
    private List<Integer> secondaryCostBrackets;

    public UserWrapper(User user) {
        super(user.getCuisines(), user.getCostBracket());
        sort();
        primaryCuisine = user.getCuisines()[0].getType();
        secondaryCuisines = new ArrayList<>(){{
            add(user.getCuisines()[1].getType());
            add(user.getCuisines()[2].getType());
        }};
        primaryCostBracket = user.getCostBracket()[0].getType();
        secondaryCostBrackets = new ArrayList<>(){{
            add(user.getCostBracket()[1].getType());
            add(user.getCostBracket()[2].getType());
        }};
    }

    private void sort() {
        Arrays.sort(getCuisines(), (c1, c2) -> c2.getNoOfOrders() - c1.getNoOfOrders());
        Arrays.sort(getCostBracket(), (c1, c2) -> c2.getNoOfOrders() - c1.getNoOfOrders());
    }
}
