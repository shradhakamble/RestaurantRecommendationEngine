package com.restaurant.recommendation;

import com.restaurant.recommendation.dto.users.User;
import com.restaurant.recommendation.manager.RestaurantRecommendationService;
import com.restaurant.recommendation.dto.CostTracking;
import com.restaurant.recommendation.dto.Cuisine;
import com.restaurant.recommendation.dto.CuisineTracking;
import com.restaurant.recommendation.dto.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RecommendationApplicationTests {

	@Test
	public void testGetRestaurantRecommendations() {
		// Create test data
		Restaurant[] restaurants = {
			new Restaurant("1", Cuisine.SouthIndian, 5, 4.2f, true, new Date()),
			new Restaurant("2", Cuisine.SouthIndian, 2, 3.1f, true, new Date()),
			new Restaurant("3", Cuisine.Chinese, 1, 4.3f, false, new Date()),
			new Restaurant("4", Cuisine.Chinese, 3, 4.0f, true, new Date()),
			new Restaurant("5", Cuisine.NorthIndian, 2, 4.5f, true, new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000))),
			new Restaurant("6", Cuisine.Thai, 2, 4.2f, false, new Date()),
			new Restaurant("7", Cuisine.Italian, 2, 4.1f, true, new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000))),
			new Restaurant("8", Cuisine.French, 3, 3.8f, true, new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000))),
			new Restaurant("9", Cuisine.SouthIndian, 3, 3.8f, false, new Date(System.currentTimeMillis() - (58 * 60 * 60 * 1000))),
			new Restaurant("10", Cuisine.Italian, 2, 4.8f, true, new Date()),
			new Restaurant("11", Cuisine.SouthIndian, 1, 4.7f, true, new Date()),
			new Restaurant("12", Cuisine.SouthIndian, 2, 4.1f, true, new Date()),
		};

		CuisineTracking[] cuisineTracking = {
			new CuisineTracking(Cuisine.SouthIndian, 10),
			new CuisineTracking(Cuisine.Chinese, 5),
			new CuisineTracking(Cuisine.Italian, 3),
			new CuisineTracking(Cuisine.Thai, 2)
		};

		CostTracking[] costTrackings = {
			new CostTracking(1, 7),
			new CostTracking(2, 8),
			new CostTracking(3, 5),
			new CostTracking(4, 2)
		};

		User user = new User(cuisineTracking, costTrackings);

		// Call the method to get recommendations
		RestaurantRecommendationService recommendationManager = new RestaurantRecommendationService();
		String[] recommendations = recommendationManager.getRestaurantRecommendations(user, restaurants);

		System.out.println(Arrays.toString(recommendations));

	}
}
