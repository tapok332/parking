package kh.karazin.parking.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Контролер для резервацій", description = "Резервації")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Додати нове бронювання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бронювання успішно додано"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addBooking(@RequestBody CreateBookingRequest request) {
        bookingService.createBooking(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отримати бронювання за ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено бронювання",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Booking> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @Operation(summary = "Оновити бронювання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бронювання успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Void> updateBooking(@PathVariable Long id, @RequestBody Booking request) {
        bookingService.updateBooking(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Почати бронювання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Бронювання успішно начато"),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @DeleteMapping("/{id}/start")
    public ResponseEntity<Void> startBooking(@PathVariable Long id) {
        bookingService.startBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Відмінити бронювання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Бронювання успішно відмінено"),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Завершити бронювання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бронювання успішно завершено"),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> finishBooking(@PathVariable Long id) {
        bookingService.finishBooking(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отримати бронювання за ID користувача")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено бронювання користувача",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Користувача не знайдено", content = @Content)
    })
    @GetMapping("/user/{userLogin}")
    public ResponseEntity<List<Booking>> getAllBookingsByUserLogin(@PathVariable String userLogin) {
        return ResponseEntity.ok(bookingService.getAllBookingsByUserLogin(userLogin));
    }

    @Operation(summary = "Отримати бронювання за ID паркувального місця")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено бронювання за паркувальним місцем",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Паркувальне місце не знайдено", content = @Content)
    })
    @GetMapping("/parking-space/{parkingSpaceId}")
    public ResponseEntity<List<Booking>> findByParkingSpaceId(@PathVariable Long parkingSpaceId) {
        return ResponseEntity.ok(bookingService.findByParkingSpaceId(parkingSpaceId));
    }
}


