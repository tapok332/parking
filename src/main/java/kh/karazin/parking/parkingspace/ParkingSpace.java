package kh.karazin.parking.parkingspace;

import jakarta.persistence.*;
import kh.karazin.parking.floor.Floor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parking_spaces")
@Getter
@Setter
@NoArgsConstructor
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    @Enumerated(EnumType.STRING)
    private ParkingSpaceType type;
    @Enumerated(EnumType.STRING)
    private ParkingSpaceStatus status = ParkingSpaceStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
}

