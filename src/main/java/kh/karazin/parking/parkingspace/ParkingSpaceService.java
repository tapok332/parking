package kh.karazin.parking.parkingspace;

import kh.karazin.parking.floor.Floor;
import kh.karazin.parking.floor.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    private final FloorRepository floorRepository;

    @Transactional
    public void addParkingSpace(CreateParkingSpaceRequest request) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setNumber(request.getNumber());
        parkingSpace.setType(request.getType());

        Floor floor = floorRepository.findById(request.getFloorId())
                .orElseThrow(() -> new RuntimeException("Floor not found with id " + request.getFloorId()));
        parkingSpace.setFloor(floor);

        parkingSpaceRepository.save(parkingSpace);
    }

    public ParkingSpace findById(Long id) {
        return parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking space not found with id " + id));
    }

    @Transactional
    public void updateParkingSpace(Long id, UpdateParkingSpaceRequest request) {
        ParkingSpace parkingSpace = findById(id);
        parkingSpace.setNumber(request.getNumber());
        parkingSpace.setType(request.getType());

        Floor floor = floorRepository.findById(request.getFloorId())
                .orElseThrow(() -> new RuntimeException("Floor not found with id " + request.getFloorId()));
        parkingSpace.setFloor(floor);

        parkingSpaceRepository.save(parkingSpace);
    }

    @Transactional
    public void deleteParkingSpace(Long id) {
        ParkingSpace parkingSpace = findById(id);
        parkingSpaceRepository.delete(parkingSpace);
    }

    public List<ParkingSpace> findAll() {
        return parkingSpaceRepository.findAll();
    }

    public List<ParkingSpace> findByFloor(Long floorId) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new RuntimeException("Floor not found with id " + floorId));
        return parkingSpaceRepository.findByFloor(floor);
    }

    @Transactional
    public void setParkingSpaceStatus(Long id, ParkingSpaceStatus status) {
        ParkingSpace parkingSpace = findById(id);
        parkingSpace.setStatus(status);
        parkingSpaceRepository.save(parkingSpace);
    }

    public Optional<ParkingSpace> isParkingSpaceAvailable(Long parkingSpaceId, LocalDateTime startTime,
                                                          LocalDateTime endTime) {
        return parkingSpaceRepository.isParkingSpaceAvailable(parkingSpaceId, startTime, endTime);
    }

    public List<ParkingSpace> findAvailableParkingSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        return parkingSpaceRepository.findAvailableParkingSpaces(startTime, endTime);
    }
}
