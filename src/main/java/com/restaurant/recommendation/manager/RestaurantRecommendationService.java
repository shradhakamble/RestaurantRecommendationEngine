package com.restaurant.recommendation.manager;

import com.restaurant.recommendation.conditions.ComputeAllConditions;
import com.restaurant.recommendation.dto.Restaurant;
import com.restaurant.recommendation.dto.users.User;
import com.restaurant.recommendation.dto.users.UserWrapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantRecommendationService {
    ComputeAllConditions checkAllConditions = new ComputeAllConditions();

    // Compute all the given conditions and get restaurant recommendations
    public String[] getRestaurantRecommendations(User user, Restaurant[] availableRestaurants) {

         // CONDITIONS -
         // 0. Process the User's primary and secondary cuisines as well as primary cost bracket and Secondary cost bracket
         // 1. Get Featured Restaurants
         // 2. PrimaryCuisine + PrimaryCostBracket + rating >= 4
         // 3. PrimaryCuisine + SecondaryCostBracket + rating >= 4.5̄
         // 4. SecondaryCuisine + PrimaryCostBracket + rating >= 4.5̄
         // 5. New Restaurants ( sorted by rating )
         // 6. PrimaryCuisine + PrimaryCostBracket + rating < 4
         // 7. PrimaryCuisine + SecondaryCostBracket + rating < 4.5̄
         // 8. SecondaryCuisine + PrimaryCostBracket + rating < 4.5̄
         // 9. Remaining Restaurants

        List<String> restaurantIds = checkAllConditions.computeAllConditions(new UserWrapper(user), availableRestaurants);
        System.out.println("restaurantIds : " + Arrays.toString(restaurantIds.toArray()));

        // condition 9. add all the remaining restaurants in the list not already sorted
        List<String> remainingRestaurants = Arrays.stream(availableRestaurants)
            .map(Restaurant::getRestaurantId)
            .filter(restaurantId -> !restaurantIds.contains(restaurantId))
            .toList();

        restaurantIds.addAll(remainingRestaurants);

        // remove duplicate restaurant Ids from the list
        Set<String> uniqueList = new LinkedHashSet<>(restaurantIds);

        return uniqueList.toArray(new String[]{});
    }

}
