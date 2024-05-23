package kh.karazin.parking.floor;

import kh.karazin.parking.parkingspace.CreateParkingSpaceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFloorRequest {
    private int level;
    private List<CreateParkingSpaceRequest> parkingSpaces;
}
