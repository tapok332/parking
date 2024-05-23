package kh.karazin.parking.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByVehicleId(Long vehicleId);

    List<Booking> findByParkingSpaceId(Long parkingSpaceId);
}

