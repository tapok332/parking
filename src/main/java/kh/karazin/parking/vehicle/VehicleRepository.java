package kh.karazin.parking.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByLicensePlate(String licensePlate);

    List<Vehicle> findAllByDriverId(Long driverId);
}
