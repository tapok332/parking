package kh.karazin.parking.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "Контролер для користувача", description = "Користувач")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Отримання клієнта по його login"
    )
    @GetMapping
    public UserInfoResponse getUserByLogin(String login) {
        return userService.getUserByLogin(login);
    }

    @Operation(
            summary = "Створення клієнта"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) {
        userService.createUser(user);
    }

    @Operation(
            summary = "Оновлення даних клієнта"
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    @Operation(
            summary = "Видалення даних клієнта"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(
            summary = "Блокування клієнта"
    )
    @PostMapping("/{id}")
    public void blockUser(@PathVariable Long id) {
        userService.blockUser(id);
    }
}

