package com.restaurant.recommendation.conditions;

import com.restaurant.recommendation.dto.Restaurant;
import com.restaurant.recommendation.dto.users.UserWrapper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Conditions {

    // Condition 1. Featured restaurants of primary cuisine and primary cost bracket.
    // If none,then all featured restaurants of primary cuisine, secondary cost and secondary cuisine, primary cost.
    public static List<String> getFeaturedRestaurantsBasedOnUserChoice(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        // Find featured restaurants + primary cuisine + primary cost bracket
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    restaurant.getCuisine().equals(userWrapper.getPrimaryCuisine()) &&
                    restaurant.getCostBracket() == userWrapper.getPrimaryCostBracket() &&
                    restaurant.isRecommended()
            ).map(Restaurant::getRestaurantId)
            .collect(Collectors.toList());

        // check if none
        if (restaurantIds.isEmpty()) {
            // fetch both featured + primaryCuisine   + secondaryCostBracket
            //       and  featured + secondaryCuisine + primaryCostBracket
            List<String> list = Arrays.stream(restaurants)
                .filter(restaurant ->
                    (
                        restaurant.isRecommended() &&
                            userWrapper.getPrimaryCuisine() == restaurant.getCuisine() &&
                            userWrapper.getSecondaryCostBrackets().contains(restaurant.getCostBracket())
                    ) || (
                        restaurant.isRecommended() &&
                            userWrapper.getSecondaryCuisines().contains(restaurant.getCuisine()) &&
                            userWrapper.getPrimaryCostBracket() == restaurant.getCostBracket()
                    )
                ).map(Restaurant::getRestaurantId)
                .toList();
            restaurantIds.addAll(list);
        }

        System.out.println("condition 1 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 2. PrimaryCuisine + PrimaryCostBracket + rating >= 4
    public static List<String> getPrimaryCuisinePrimaryCostRatingGE4(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    userWrapper.getPrimaryCuisine().equals(restaurant.getCuisine()) &&
                    userWrapper.getPrimaryCostBracket() == restaurant.getCostBracket() &&
                    restaurant.getRating() >= 4
            ).map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 2 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 3. PrimaryCuisine + SecondaryCostBracket + rating >= 4.5̄
    public static List<String> getPrimaryCuisineSecondaryCostRatingGE4_5(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    userWrapper.getPrimaryCuisine().equals(restaurant.getCuisine()) &&
                    userWrapper.getSecondaryCostBrackets().contains(restaurant.getCostBracket()) &&
                    restaurant.getRating() >= 4.5
            ).map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 3 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 4. SecondaryCuisine + PrimaryCostBracket + rating >= 4.5̄
    public static List<String> getSecondaryCuisinePrimaryCostRatingGE4_5(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    userWrapper.getSecondaryCuisines().contains(restaurant.getCuisine()) &&
                    userWrapper.getPrimaryCostBracket() == restaurant.getCostBracket() &&
                    restaurant.getRating() >= 4.5
            ).map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 4 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 5. New Restaurants ( sorted by rating )
    public static List<String> getTop4NewRestaurants(Restaurant[] restaurants, UserWrapper userWrapper) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    restaurant.getOnboardedTime().after(
                        new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000))
                    )
            )
            .sorted(Comparator.comparing(Restaurant::getRating).reversed())
            .limit(4)
            .map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 5 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 6. PrimaryCuisine + PrimaryCostBracket + rating < 4
    public static List<String> getPrimaryCuisinePrimaryCostRatingL4(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    userWrapper.getPrimaryCuisine().equals(restaurant.getCuisine()) &&
                    userWrapper.getPrimaryCostBracket() == restaurant.getCostBracket() &&
                    restaurant.getRating() < 4
            ).map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 6 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 7. PrimaryCuisine + SecondaryCostBracket + rating < 4.5̄
    public static List<String> getPrimaryCuisineSecondaryCostRatingL4_5(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    userWrapper.getPrimaryCuisine().equals(restaurant.getCuisine()) &&
                    userWrapper.getSecondaryCostBrackets().contains(restaurant.getCostBracket()) &&
                    restaurant.getRating() < 4.5
            ).map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 7 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

    // Condition 8. SecondaryCuisine + PrimaryCostBracket + rating < 4.5̄
    public static List<String> getSecondaryCuisinePrimaryCostRatingL4_5(
        Restaurant[] restaurants,
        UserWrapper userWrapper
    ) {
        List<String> restaurantIds = Arrays.stream(restaurants)
            .filter(restaurant ->
                    userWrapper.getSecondaryCuisines().contains(restaurant.getCuisine()) &&
                    userWrapper.getPrimaryCostBracket() == restaurant.getCostBracket() &&
                    restaurant.getRating() < 4.5
            ).map(Restaurant::getRestaurantId)
            .toList();

        System.out.println("condition 8 : " + Arrays.toString(restaurantIds.toArray()));
        return restaurantIds;
    }

}

