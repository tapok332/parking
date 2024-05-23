package kh.karazin.parking.vehicle;

import jakarta.persistence.*;
import kh.karazin.parking.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private String make;
    private String model;
    private String color;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;
}

