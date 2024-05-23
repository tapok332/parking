package kh.karazin.parking.parkingspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParkingSpaceRequest {
    private String number;
    private ParkingSpaceType type;
    private Long floorId;
}

