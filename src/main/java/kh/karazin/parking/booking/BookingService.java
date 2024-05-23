package kh.karazin.parking.booking;

import jakarta.transaction.Transactional;
import kh.karazin.parking.billing.BillingService;
import kh.karazin.parking.parkingspace.ParkingSpaceService;
import kh.karazin.parking.user.UserService;
import kh.karazin.parking.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ParkingSpaceService parkingSpaceService;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final VehicleService vehicleService;

    public List<Booking> getAllBookingsByUserLogin(String userLogin) {
        return userService.findUserByLogin(userLogin).getVehicles().stream()
                .flatMap(vehicle -> bookingRepository.findAllByVehicleId(vehicle.getId()).stream())
                .toList();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public void createBooking(CreateBookingRequest createBookingRequest) {
        var parkingSpace = parkingSpaceService.isParkingSpaceAvailable(createBookingRequest.parkingSpaceId(),
                                                                       createBookingRequest.startDateTime(),
                                                                       createBookingRequest.endDateTime())
                .orElseThrow(() -> new RuntimeException(
                        "Parking space not available with id: " + createBookingRequest.parkingSpaceId())
                );

        var booking = Booking.builder()
                .parkingSpace(parkingSpace)
                .vehicle(vehicleService.findById(createBookingRequest.vehicleId()))
                .status(BookingStatus.ACTIVE)
                .startTime(createBookingRequest.startDateTime())
                .endTime(createBookingRequest.endDateTime())
                .build();

        bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setParkingSpace(bookingDetails.getParkingSpace());
            booking.setVehicle(bookingDetails.getVehicle());
            booking.setStartTime(bookingDetails.getStartTime());
            booking.setEndTime(bookingDetails.getEndTime());
            booking.setStatus(bookingDetails.getStatus());
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    public void startBooking(Long bookingId) {
        bookingRepository.findById(bookingId)
                .ifPresentOrElse(
                        booking -> {
                            booking.setStatus(BookingStatus.PENDING);
                            bookingRepository.save(booking);
                        },
                        () -> {
                            throw new RuntimeException("Booking not found with id " + bookingId);
                        }
                );
    }

    public void finishBooking(Long bookingId) {
        bookingRepository.findById(bookingId)
                .ifPresentOrElse(
                        this::finish,
                        () -> {
                            throw new RuntimeException("Booking not found with id " + bookingId);
                        }
                );
    }

    private void finish(Booking booking) {
        var currentTime = LocalDateTime.now();
        var bookingStatus = currentTime.isAfter(booking.getEndTime())
                ? BookingStatus.OVERDUE_COMPLETED
                : BookingStatus.COMPLETED;

        booking.setStatus(bookingStatus);
        if (bookingStatus == BookingStatus.OVERDUE_COMPLETED) {
            userService.increasePenalty(
                    booking.getVehicle().getDriver(),
                    BillingService.calculateOverdueAmount(booking.getEndTime(), currentTime)
            );
        }
        bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        bookingRepository.findById(id)
                .ifPresentOrElse(
                        booking -> {
                            booking.setStatus(BookingStatus.CANCELLED);
                            bookingRepository.save(booking);
                        },
                        () -> {
                            throw new RuntimeException("Booking not found with id " + id);
                        }
                );
    }

    public List<Booking> findByParkingSpaceId(Long parkingSpaceId) {
        return bookingRepository.findByParkingSpaceId(parkingSpaceId);
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }
}

