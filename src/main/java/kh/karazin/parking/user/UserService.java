package kh.karazin.parking.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final BookingService bookingService;

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found with login " + login));
    }

    public UserInfoResponse getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(User::asResponse)
//                .map(response -> {
//                    var bookings = new ArrayList<Booking>();
//                    response.getVehicles()
//                            .forEach(vehicle -> bookings.addAll(bookingService.getAllBookingsByVehicleId(vehicle.getId())));
//                    response.setBookings(bookings);
//                    return response;
//                })
                .orElseThrow(() -> new RuntimeException("User not found with login " + login));
    }

    public void increasePenalty(User user, BigDecimal penalty) {
        var payload = user.getPayload();

        payload.setPenalty(payload.getPenalty().add(penalty));

        userRepository.save(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setLogin(userDetails.getLogin());
            user.setPasswordHash(userDetails.getPasswordHash());
            user.setName(userDetails.getName());
            user.setPayload(userDetails.getPayload());
            user.setAuthorities(userDetails.getAuthorities());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void blockUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        user.block();

        userRepository.save(user);
    }
}
