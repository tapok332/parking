package kh.karazin.parking.parkinglot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotRequest {
    private String name;
    private String location;
    private int totalFloors;
}

