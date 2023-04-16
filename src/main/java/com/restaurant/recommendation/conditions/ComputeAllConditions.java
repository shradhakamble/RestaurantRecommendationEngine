package com.restaurant.recommendation.conditions;

import com.restaurant.recommendation.dto.Cuisine;
import com.restaurant.recommendation.dto.Restaurant;
import com.restaurant.recommendation.dto.users.UserWrapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Calculate all the conditions one by one except the condition
 *      9. All restaurants of any cuisine, any cost bracket
 *
 * Using CompletableFuture class, that helps to do computations asynchronously
 * by running all the conditions in parallel and at the end combines all the computations
 * in order to get the final result.
 *
 * Also, with this approach the code is extensible and maintainable at the same time,
 * one just have to add a condition without touching the core logic of other conditions.
 *
 */

@Service
public class ComputeAllConditions {
    public List<String> computeAllConditions(UserWrapper userWrapper, Restaurant[] availableRestaurants) {

        // 0. Process the User's primary and secondary cuisines as well as
        //    primary cost bracket and Secondary cost bracket
        Cuisine primaryCuisine = userWrapper.getPrimaryCuisine();
        List<Cuisine> secondaryCuisines = userWrapper.getSecondaryCuisines();
        int primaryCost = userWrapper.getPrimaryCostBracket();
        List<Integer> secondaryCostBracket = userWrapper.getSecondaryCostBrackets();

        System.out.println("primaryCuisine : " + primaryCuisine);
        System.out.println("secondaryCuisines : " + Arrays.toString(secondaryCuisines.toArray()));
        System.out.println("primaryCost : " + primaryCost);
        System.out.println("secondaryCostBracket : " + Arrays.toString(secondaryCostBracket.toArray()));

        /// 1. Featured restaurants of primary cuisine and primary cost bracket.
        // If none,then all featured restaurants of primary cuisine, secondary cost and secondary cuisine, primary cost.
        CompletableFuture<List<String>> condition1 = CompletableFuture.supplyAsync(
            () -> Conditions.getFeaturedRestaurantsBasedOnUserChoice(
                availableRestaurants, userWrapper
            )
        );

        // 2. All restaurants of Primary cuisine, primary cost bracket with rating >= 4
        CompletableFuture<List<String>> condition2 = CompletableFuture.supplyAsync(
            () -> Conditions.getPrimaryCuisinePrimaryCostRatingGE4(
                availableRestaurants, userWrapper
            )
        );

        // 3. All restaurants of Primary cuisine, secondary cost bracket with rating >= 4.5
        CompletableFuture<List<String>> condition3 = CompletableFuture.supplyAsync(
            () -> Conditions.getPrimaryCuisineSecondaryCostRatingGE4_5(
                availableRestaurants, userWrapper
            )
        );

        // 4. All restaurants of secondary cuisine, primary cost bracket with rating >= 4.5
        CompletableFuture<List<String>> condition4 = CompletableFuture.supplyAsync(
            () -> Conditions.getSecondaryCuisinePrimaryCostRatingGE4_5(
                availableRestaurants, userWrapper
            )
        );

        // 5. Top 4 newly created restaurants by rating
        CompletableFuture<List<String>> condition5 = CompletableFuture.supplyAsync(
            () -> Conditions.getTop4NewRestaurants(availableRestaurants, userWrapper)
        );

        // 6. All restaurants of Primary cuisine, primary cost bracket with rating < 4
        CompletableFuture<List<String>> condition6 = CompletableFuture.supplyAsync(
            () -> Conditions.getPrimaryCuisinePrimaryCostRatingL4(
                availableRestaurants, userWrapper
            )
        );

        // 7. All restaurants of Primary cuisine, secondary cost bracket with rating < 4.5
        CompletableFuture<List<String>> condition7 = CompletableFuture.supplyAsync(
            () -> Conditions.getPrimaryCuisineSecondaryCostRatingL4_5(
                availableRestaurants, userWrapper
            )
        );

        // 8. All restaurants of secondary cuisine, primary cost bracket with rating < 4.5
        CompletableFuture<List<String>> condition8 = CompletableFuture.supplyAsync(
            () -> Conditions.getSecondaryCuisinePrimaryCostRatingL4_5(
                availableRestaurants, userWrapper
            )
        );

        // Can be replaced with interfaces and reflections to avoid
        // such initialization
        CompletableFuture<List<String>>[] conditions = new CompletableFuture[]{
            condition1,
            condition2,
            condition3,
            condition4,
            condition5,
            condition6,
            condition7,
            condition8
        };

        CompletableFuture<List<String>> listCompletableFuture = getListCompletableFuture(conditions);
        return listCompletableFuture.join();
    }

    // combining the results of all the conditions in order from 1 ... n and return the list
    private static CompletableFuture<List<String>> getListCompletableFuture(
        CompletableFuture<List<String>>[] conditions
    ) {
        return CompletableFuture.allOf(conditions)
            .thenApply(
                t -> Stream.of(conditions)
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .collect(Collectors.toList())
            );
    }
}
