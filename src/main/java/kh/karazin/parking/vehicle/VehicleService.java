package kh.karazin.parking.vehicle;

import jakarta.transaction.Transactional;
import kh.karazin.parking.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    @Transactional
    public void addVehicle(VehicleRequest request) {
        if (vehicleRepository.existsByLicensePlate(request.licensePlate())) {
            throw new RuntimeException();
        }

        var vehicle = Vehicle.builder()
                .licensePlate(request.licensePlate())
                .model(request.model())
                .make(request.make())
                .color(request.color())
                .driver(userService.findUserByLogin(request.userLogin()))
                .build();

        vehicleRepository.save(vehicle);
    }

    public Vehicle findById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow();
    }

    @Transactional
    public void updateVehicle(Long vehicleId, VehicleRequest request) {
        var vehicle = findById(vehicleId);

        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setModel(request.model());
        vehicle.setMake(request.make());
        vehicle.setColor(request.color());

        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long vehicleId) {
        var vehicle = findById(vehicleId);
        vehicleRepository.delete(vehicle);
    }

    public VehicleResponse getVehicleById(Long id) {
        return new VehicleResponse(findById(id));
    }
}
