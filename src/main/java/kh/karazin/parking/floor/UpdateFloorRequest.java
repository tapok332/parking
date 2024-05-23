package kh.karazin.parking.floor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFloorRequest {
    private int level;
    private Long parkingLotId;
}

