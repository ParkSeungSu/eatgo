package kr.co.parkseungsu.eatgo.application;


import kr.co.parkseungsu.eatgo.domain.Reservation;
import kr.co.parkseungsu.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationService {


    private ReservationRepository reservationRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository=reservationRepository;
    }

    public List<Reservation> getReservations(long restaurantId) {
        return null;
    }
}
