package kr.co.parkseungsu.eatgo.application;

import kr.co.parkseungsu.eatgo.domain.Review;
import kr.co.parkseungsu.eatgo.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ReviewServiceTest {


    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        reviewService=new ReviewService(reviewRepository);
    }
    @Test
    public void addReview(){
        Review review=Review.builder()
                .name("Park")
                .score(3)
                .description("Delicious")
                .build();
        reviewService.addReview(1004L,review);
        verify(reviewRepository).save(any());
    }

}