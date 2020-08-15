package kr.co.parkseungsu.eatgo.application;

import kr.co.parkseungsu.eatgo.domain.Reservation;
import kr.co.parkseungsu.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

public class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationService=new ReservationService(reservationRepository);
    }

    @Test
    public void getReservation(){
        Long restaurantId=1004L;

        List<Reservation> reservations=
                reservationService.getReservations(restaurantId);

        verify(reservationRepository).findAllByRestaurantId(restaurantId);
        
    }

}