package kr.co.parkseungsu.eatgo.application;


import kr.co.parkseungsu.eatgo.domain.Reservation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReservationService {
    public Reservation addReservation(Long restaurantId, Long userId, String name,
                                      String date, String time, Integer partySize) {
        //TODO:
        return Reservation.builder()
                .restaurantId(restaurantId)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();
    }
}
