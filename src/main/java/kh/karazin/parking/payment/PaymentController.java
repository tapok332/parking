package kh.karazin.parking.payment;

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
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Контролер для оплат", description = "Оплати")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Додати нову оплату")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оплату успішно додано"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addPayment(@RequestBody PaymentRequest request) {
        paymentService.addPayment(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отримати оплату за ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено оплату",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Payment.class))}),
            @ApiResponse(responseCode = "404", description = "Оплату не знайдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @Operation(summary = "Оновити оплату")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оплату успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "404", description = "Оплату не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Void> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest request) {
        paymentService.updatePayment(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Видалити оплату")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Оплату успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Оплату не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отримати всі оплати")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено всі оплати",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Payment.class))})
    })
    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @Operation(summary = "Отримати оплати за ID користувача")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено оплати користувача",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Payment.class))}),
            @ApiResponse(responseCode = "404", description = "Користувача не знайдено", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.findByUserId(userId));
    }

    @Operation(summary = "Отримати оплати за ID бронювання")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено оплати за бронювання",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Payment.class))}),
            @ApiResponse(responseCode = "404", description = "Бронювання не знайдено", content = @Content)
    })
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> findByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.findByBookingId(bookingId));
    }
}

