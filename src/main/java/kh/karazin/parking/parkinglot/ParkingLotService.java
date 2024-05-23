package kh.karazin.parking.parkinglot;

import kh.karazin.parking.floor.CreateFloorRequest;
import kh.karazin.parking.floor.Floor;
import kh.karazin.parking.floor.FloorRepository;
import kh.karazin.parking.parkingspace.CreateParkingSpaceRequest;
import kh.karazin.parking.parkingspace.ParkingSpace;
import kh.karazin.parking.parkingspace.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final FloorRepository floorRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional
    public void addParkingLot(ParkingLotRequest request) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(request.getName());
        parkingLot.setLocation(request.getLocation());
        parkingLot.setTotalFloors(request.getTotalFloors());

        parkingLotRepository.save(parkingLot);
    }

    public ParkingLot findById(Long id) {
        return parkingLotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking lot not found with id " + id));
    }

    @Transactional
    public void updateParkingLot(Long id, ParkingLotRequest request) {
        ParkingLot parkingLot = findById(id);
        parkingLot.setName(request.getName());
        parkingLot.setLocation(request.getLocation());
        parkingLot.setTotalFloors(request.getTotalFloors());

        parkingLotRepository.save(parkingLot);
    }

    @Transactional
    public void deleteParkingLot(Long id) {
        ParkingLot parkingLot = findById(id);
        parkingLotRepository.delete(parkingLot);
    }

    public List<ParkingLot> findAll() {
        return parkingLotRepository.findAll();
    }

    @Transactional
    public void addFullParkingLot(CreateFullParkingLotRequest request) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(request.getName());
        parkingLot.setLocation(request.getLocation());
        parkingLot.setTotalFloors(request.getFloors().size());

        parkingLotRepository.save(parkingLot);

        for (CreateFloorRequest floorRequest : request.getFloors()) {
            Floor floor = new Floor();
            floor.setLevel(floorRequest.getLevel());
            floor.setParkingLot(parkingLot);

            floorRepository.save(floor);

            for (CreateParkingSpaceRequest spaceRequest : floorRequest.getParkingSpaces()) {
                ParkingSpace parkingSpace = new ParkingSpace();
                parkingSpace.setNumber(spaceRequest.getNumber());
                parkingSpace.setType(spaceRequest.getType());
                parkingSpace.setFloor(floor);

                parkingSpaceRepository.save(parkingSpace);
            }
        }
    }
}

