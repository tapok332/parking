package kh.karazin.parking.user;

import kh.karazin.parking.booking.Booking;
import kh.karazin.parking.vehicle.Vehicle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserInfoResponse {
    private String login;
    private String name;
    private UserPayload payload;
    private List<Role> authorities;
    private List<Vehicle> vehicles;
    private List<Booking> bookings;
}
