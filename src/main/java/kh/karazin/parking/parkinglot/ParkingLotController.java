package kh.karazin.parking.parkinglot;

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
@RequestMapping("/api/parking-lots")
@RequiredArgsConstructor
@Tag(name = "Контролер для адмініструванння паркінгом", description = "Паркінг")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @Operation(summary = "Додати новий паркінг")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Паркінг успішно додано"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addParkingLot(@RequestBody ParkingLotRequest request) {
        parkingLotService.addParkingLot(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Отримати паркінг за ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено паркінг",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingLot.class))}),
            @ApiResponse(responseCode = "404", description = "Паркінг не знайдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParkingLot> findById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingLotService.findById(id));
    }

    @Operation(summary = "Оновити паркінг")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Паркінг успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "404", description = "Паркінг не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Void> updateParkingLot(@PathVariable Long id, @RequestBody ParkingLotRequest request) {
        parkingLotService.updateParkingLot(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Видалити паркінг")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Паркінг успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Паркінг не знайдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        parkingLotService.deleteParkingLot(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отримати всі паркінги")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Знайдено всі паркінги",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParkingLot.class))})
    })
    @GetMapping
    public ResponseEntity<List<ParkingLot>> findAll() {
        return ResponseEntity.ok(parkingLotService.findAll());
    }

    @Operation(summary = "Додати новий паркінг разом з поверхами та паркувальними місцями")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Паркінг разом з поверхами та паркувальними місцями успішно додано"),
            @ApiResponse(responseCode = "400", description = "Невірний ввід", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @PostMapping("/add-full")
    public ResponseEntity<Void> addFullParkingLot(@RequestBody CreateFullParkingLotRequest request) {
        parkingLotService.addFullParkingLot(request);
        return ResponseEntity.ok().build();
    }
}
