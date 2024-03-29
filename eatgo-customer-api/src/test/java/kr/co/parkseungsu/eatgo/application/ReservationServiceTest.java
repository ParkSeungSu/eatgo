package kr.co.parkseungsu.eatgo.application;

import kr.co.parkseungsu.eatgo.domain.Reservation;
import kr.co.parkseungsu.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ReservationServiceTest {


    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        reservationService=new ReservationService(reservationRepository);
    }
    @Test
    public void addReservation(){

        Long restaurantId=369L;
        Long userId=1004L;
        String name="John";
        String date="2020-12-24";
        String time="20:00";
        Integer partySize=20;

        Reservation mockReservation=Reservation.builder()
                .restaurantId(restaurantId)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize).build();

        given(reservationRepository.save(any()))
                .will(invocation -> {
                    Reservation reservation=invocation.getArgument(0);
                    return reservation;
                });

        Reservation reservation=reservationService.addReservation(
                restaurantId,userId,name,date,time,partySize);

        assertThat(reservation.getName(),is(name));

        verify(reservationRepository).save(any(Reservation.class));
    }

}