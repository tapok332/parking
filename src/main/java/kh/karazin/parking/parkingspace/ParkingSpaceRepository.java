package kh.karazin.parking.parkingspace;

import kh.karazin.parking.floor.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    @Query(value = """
            SELECT *
            FROM parking_spaces ps
            WHERE ps.status = 'AVAILABLE'
            AND NOT EXISTS (
                SELECT 1
                FROM bookings b
                WHERE b.parking_space_id = ps.id
                AND b.status IN ('ACTIVE', 'PENDING')
                AND (
                    (b.start_time <= :startTime AND b.end_time > :startTime)
                    OR
                    (b.start_time < :endTime AND b.end_time >= :endTime)
                    OR
                    (:startTime <= b.start_time AND :endTime > b.start_time)
                    OR
                    (:startTime < b.end_time AND :endTime >= b.end_time)
                )
            )
            """, nativeQuery = true)
    List<ParkingSpace> findAvailableParkingSpaces(LocalDateTime startTime, LocalDateTime endTime);


    @Query(value = """
            SELECT *
            FROM parking_spaces ps
            WHERE ps.id = :parkingSpaceId
            AND ps.status = 'AVAILABLE'
            AND NOT EXISTS (
                SELECT 1
                FROM bookings b
                WHERE b.parking_space_id = ps.id
                AND b.status IN ('ACTIVE', 'PENDING')
                AND (
                    (b.start_time <= :startTime AND b.end_time > :startTime)
                    OR
                    (b.start_time < :endTime AND b.end_time >= :endTime)
                    OR
                    (:startTime <= b.start_time AND :endTime > b.start_time)
                    OR
                    (:startTime < b.end_time AND :endTime >= b.end_time)
                )
            )""", nativeQuery = true)
    Optional<ParkingSpace> isParkingSpaceAvailable(Long parkingSpaceId, LocalDateTime startTime, LocalDateTime endTime);

    List<ParkingSpace> findByFloor(Floor floor);
}
