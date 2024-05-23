package kh.karazin.parking.booking;

import java.time.LocalDateTime;

record CreateBookingRequest(
        Long vehicleId,
        Long parkingSpaceId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
