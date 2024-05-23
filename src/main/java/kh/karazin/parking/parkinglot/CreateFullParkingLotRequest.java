package kh.karazin.parking.parkinglot;

import kh.karazin.parking.floor.CreateFloorRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFullParkingLotRequest {
    private String name;
    private String location;
    private List<CreateFloorRequest> floors;
}

