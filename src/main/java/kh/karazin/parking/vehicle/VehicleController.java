package kh.karazin.parking.vehicle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
@Tag(name = "Контролер для транспортних засобів", description = "Управління транспортними засобами")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(
            description = "Треба мати JWT токен з ролью CLIENT",
            summary = "Створення нового транспортного засобу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Транспортний засіб створено успішно"),
            @ApiResponse(responseCode = "400", description = "Невірний запит"),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера")
    })
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVehicle(@RequestBody VehicleRequest request) {
        vehicleService.addVehicle(request);
    }

    @Operation(
            description = "Треба мати JWT токен з ролью CLIENT або ADMIN",
            summary = "Оновлення транспортного засобу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортний засіб оновлено успішно"),
            @ApiResponse(responseCode = "400", description = "Невірний запит"),
            @ApiResponse(responseCode = "404", description = "Транспортний засіб не знайдено"),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequest request) {
        vehicleService.updateVehicle(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Треба мати JWT токен з ролью ADMIN",
            summary = "Видалення транспортного засобу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Транспортний засіб видалено успішно"),
            @ApiResponse(responseCode = "404", description = "Транспортний засіб не знайдено"),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Треба мати JWT токен з ролью CLIENT або ADMIN",
            summary = "Отримання інформації про транспортний засіб за ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Інформація про транспортний засіб отримана успішно"),
            @ApiResponse(responseCode = "404", description = "Транспортний засіб не знайдено"),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        VehicleResponse response = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(response);
    }
}
