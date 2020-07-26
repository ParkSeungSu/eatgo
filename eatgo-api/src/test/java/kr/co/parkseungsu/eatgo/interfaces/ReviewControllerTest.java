package kr.co.parkseungsu.eatgo.interfaces;

import kr.co.parkseungsu.eatgo.application.ReviewService;
import kr.co.parkseungsu.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidAttributes() throws Exception {
        given(reviewService.addReview(eq(1L),any())).willReturn(
                Review.builder()
                        .id(1004L)
                        .name("Park")
                        .score(3)
                        .description("delicious")
                        .build()
        );
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Park\",\"score\":3,\"description\":\"delicious\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1/reviews/1004"));
        verify(reviewService).addReview(eq(1L),any());
    }

    @Test
    public void createWithInvalidAttributes() throws Exception {
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
        verify(reviewService,never()).addReview(eq(1L),any());
    }

}