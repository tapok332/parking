package kh.karazin.parking.user;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import kh.karazin.parking.vehicle.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String passwordHash;
    private String name;
    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private UserPayload payload;
    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private List<Role> authorities;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;
    private boolean isActive = true;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    UserInfoResponse asResponse() {
        return UserInfoResponse.builder()
                .login(login)
                .name(name)
                .payload(payload)
                .authorities(authorities)
                .vehicles(vehicles)
                .build();
    }

    void block() {
        this.isActive = false;
    }
}

