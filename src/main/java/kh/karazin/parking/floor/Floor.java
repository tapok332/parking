package kh.karazin.parking.floor;

import jakarta.persistence.*;
import kh.karazin.parking.parkinglot.ParkingLot;
import kh.karazin.parking.parkingspace.ParkingSpace;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "floors")
@Data
@NoArgsConstructor
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingSpace> parkingSpaces;
}
