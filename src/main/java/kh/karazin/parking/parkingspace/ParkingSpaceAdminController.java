package kh.karazin.parking.parkingspace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/parking-spaces")
@RequiredArgsConstructor
@Tag(name = "Контролер для адмініструванння паркомісцем", description = "Паркомісце")
public class ParkingSpaceAdminController {

    private final ParkingSpaceService parkingSpaceService;

    @Operation(summary = "Додати нове паркувальне місце")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Паркувальне місце успішно додано"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addParkingSpace(@RequestBody CreateParkingSpaceRequest request) {
        parkingSpaceService.addParkingSpace(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отримати паркувальне місце за ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено паркувальне місце",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpace.class))}),
            @ApiResponse(responseCode = "404", description = "Паркувальне місце не знайдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> findById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpaceService.findById(id));
    }

    @Operation(summary = "Оновити паркувальне місце")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Паркувальне місце успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "404", description = "Паркувальне місце не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Void> updateParkingSpace(@PathVariable Long id,
                                                   @RequestBody UpdateParkingSpaceRequest request) {
        parkingSpaceService.updateParkingSpace(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Видалити паркувальне місце")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Паркувальне місце успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Паркувальне місце не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        parkingSpaceService.deleteParkingSpace(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отримати всі паркувальні місця")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено всі паркувальні місця",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpace.class))})
    })
    @GetMapping
    public ResponseEntity<List<ParkingSpace>> findAll() {
        return ResponseEntity.ok(parkingSpaceService.findAll());
    }

    @Operation(summary = "Отримати паркувальні місця за ID поверху")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено паркувальні місця",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpace.class))}),
            @ApiResponse(responseCode = "404", description = "Поверх не знайдено", content = @Content)
    })
    @GetMapping("/floor/{floorId}")
    public ResponseEntity<List<ParkingSpace>> findByFloor(@PathVariable Long floorId) {
        return ResponseEntity.ok(parkingSpaceService.findByFloor(floorId));
    }

    @Operation(summary = "Встановити статус паркувального місця")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус паркувального місця успішно оновлено"),
            @ApiResponse(responseCode = "404", description = "Паркувальне місце не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> setParkingSpaceStatus(@PathVariable Long id, @RequestParam ParkingSpaceStatus status) {
        parkingSpaceService.setParkingSpaceStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отримати всі доступні паркувальні місця на заданий проміжок часу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено всі доступні паркувальні місця",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpace.class))})
    })
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpace>> findAvailableParkingSpaces(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        List<ParkingSpace> availableSpaces = parkingSpaceService.findAvailableParkingSpaces(startTime, endTime);
        return ResponseEntity.ok(availableSpaces);
    }

    @Operation(summary = "Перевірити, чи доступне конкретне паркувальне місце на заданий проміжок часу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Перевірка доступності",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Optional.class))}),
            @ApiResponse(responseCode = "404", description = "Паркувальне місце не знайдено", content = @Content)
    })
    @GetMapping("/available/{id}")
    public ResponseEntity<Optional<ParkingSpace>> isParkingSpaceAvailable(
            @PathVariable Long id,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        Optional<ParkingSpace> parkingSpace = parkingSpaceService.isParkingSpaceAvailable(id, startTime, endTime);
        return ResponseEntity.ok(parkingSpace);
    }
}

