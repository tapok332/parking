package kh.karazin.parking.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleResponse {
    private Long id;
    private String licensePlate;
    private String model;
    private String make;
    private String color;
    private String driverName;

    public VehicleResponse(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.licensePlate = vehicle.getLicensePlate();
        this.model = vehicle.getModel();
        this.make = vehicle.getMake();
        this.color = vehicle.getColor();
        this.driverName = vehicle.getDriver().getName();
    }
}
