package kr.co.parkseungsu.eatgo.interfaces;

import kr.co.parkseungsu.eatgo.application.RestaurantService;
import kr.co.parkseungsu.eatgo.domain.MenuItem;
import kr.co.parkseungsu.eatgo.domain.Restaurant;
import kr.co.parkseungsu.eatgo.domain.RestaurantNotFoundException;
import kr.co.parkseungsu.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1004")))
                .andExpect(content().string(containsString(
                        "\"name\":\"Bob zip\"")));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        MenuItem menuItem= MenuItem.builder()
                .name("Kimchi")
                .build();
        restaurant.setMenuItems(Arrays.asList(menuItem));
        Review review=Review.builder().name("Park").score(5).description("Good").build();

        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1004")))
                .andExpect(content().string(containsString(
                        "\"name\":\"Bob zip\"")))
                .andExpect(content().string((containsString(
                        "Kimchi"))))
                .andExpect(content().string(containsString("Good")));

    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
        .andExpect(content().string("{}"));
    }

}