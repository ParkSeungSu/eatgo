package kr.co.parkseungsu.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestaurantTest {

    @Test
    public void creation() {
        Restaurant restaurant=Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        assertThat(restaurant.getId(),is(1004L));
        assertThat(restaurant.getName(),is("Bob zip"));
        assertThat(restaurant.getAddress(),is("Seoul"));
    }

    @Test
    public void getInformation() {
        Restaurant restaurant=new Restaurant(1004L,"Bob zip","Seoul");
        assertThat(restaurant.getInformation(),is("Bob zip in Seoul"));
    }
}