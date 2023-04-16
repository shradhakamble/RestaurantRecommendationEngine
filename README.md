# Read Me First

# Restaurant Recommendation Engine
Build an application for restaurant recommendation to the food delivery app.

The recommendations are computed using a few condition, that needs to be executed in some specific order, 
and sort them to get the correct set of recommended restaurants.

# Approach -
There are two approaches to implement this problem statement -
    1. Sequential execution of condition logic using a single thread
    2. Parallel execution of condition logic using multiple threads

With approach 1, the size of the loop reduces with each sorting logic execution but the overall time 
taken for the recommendation to be processed will be a lot, hence the loading time in the app will be more 
for showing the recommendations. 

With approach 2 the size of the loop remains the same for all sorting logic execution but the overall time 
taken for the recommendation to be processed is the least due to parallel execution of all the conditions. 
However, there is a CPU cost and overhead to it. 

Assuming this logic will be processed on an external server and not on the user devices, and looking at 
the use-case where faster recommendations are key I have decided to go with approach 2 
i.e., Parallel execution of condition logic using multiple threads. 

# Steps to run the application
 - Go to /recommendation/src/test/java/com/restaurant/recommendation
 - Run ./RecommendationApplicationTests.java file
 - Add test case if required. 

