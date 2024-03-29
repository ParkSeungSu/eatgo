package kr.co.parkseungsu.eatgo.application;

import kr.co.parkseungsu.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        mockRestaurantRepository();
        mockMenuRepository();
        mockReviewRepository();
        
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository,reviewRepository);

    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Wonju")
                .build();

        restaurants.add(restaurant);
        given(restaurantRepository.findAllByAddressContainingAndCategoryId("Wonju",1L))
                .willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

    }

    private void mockMenuRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("Kimchi").build());
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews=new ArrayList<>();
        reviews.add(Review.builder().name("BeRyong").score(1).description("Bad").build());
        given(reviewRepository.findAllByRestaurantId(1004L))
                .willReturn(reviews);
    }

    @Test
    public void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));
        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));

        Review review=restaurant.getReviews().get(0);
        assertThat(review.getDescription(), is("Bad"));
    }
    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithNotExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(404L);
    }

    @Test
    public void getRestaurants() {
        String region="Wonju";
        Long categoryId=1L;
        List<Restaurant> restaurants = restaurantService.getRestaurants(region,categoryId );

        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));

    }

    @Test
    public void addRestaurant() {

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant=invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });
        Restaurant restaurant = Restaurant.builder()
                            .name("BeRyong")
                            .address("Seoul")
                            .build();
        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));

    }
    @Test
    public void updateRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        restaurantService.updateRestaurant(1004L,"Sul zip","Wonju");
        assertThat(restaurant.getName(),is("Sul zip"));
        assertThat(restaurant.getAddress(),is("Wonju"));
    }
}