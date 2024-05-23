CREATE TABLE parking_lots
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    location VARCHAR(200) NOT NULL
);

COMMENT ON TABLE parking_lots IS 'Таблиця з інформацією про паркінги';
COMMENT ON COLUMN parking_lots.id IS 'Унікальний ідентифікатор паркінгу';
COMMENT ON COLUMN parking_lots.name IS 'Назва паркінгу';
COMMENT ON COLUMN parking_lots.location IS 'Місцезнаходження паркінгу';

CREATE TABLE floors
(
    id             BIGSERIAL PRIMARY KEY,
    level          INT    NOT NULL,
    parking_lot_id BIGINT NOT NULL,
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots (id)
);

COMMENT ON TABLE floors IS 'Таблиця з інформацією про поверхи паркінгу';
COMMENT ON COLUMN floors.id IS 'Унікальний ідентифікатор поверху';
COMMENT ON COLUMN floors.level IS 'Рівень поверху';
COMMENT ON COLUMN floors.parking_lot_id IS 'Ідентифікатор паркінгу, до якого належить поверх';

CREATE TABLE parking_spaces
(
    id       BIGSERIAL PRIMARY KEY,
    number   VARCHAR(10) NOT NULL,
    type     VARCHAR(20) NOT NULL,
    floor_id BIGINT      NOT NULL,
    FOREIGN KEY (floor_id) REFERENCES floors (id)
);

COMMENT ON TABLE parking_spaces IS 'Таблиця з інформацією про паркувальні місця';
COMMENT ON COLUMN parking_spaces.id IS 'Унікальний ідентифікатор паркувального місця';
COMMENT ON COLUMN parking_spaces.number IS 'Номер паркувального місця';
COMMENT ON COLUMN parking_spaces.type IS 'Тип паркувального місця (звичайне, VIP, для інвалідів і т.д.)';
COMMENT ON COLUMN parking_spaces.floor_id IS 'Ідентифікатор поверху, на якому знаходиться паркувальне місце';

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    login         VARCHAR(10) NOT NULL UNIQUE,
    password_hash VARCHAR     NOT NULL,
    name          VARCHAR(50) NOT NULL,
    payload       jsonb,
    authorities   jsonb,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE users IS 'Таблиця з інформацією про користувачів';
COMMENT ON COLUMN users.id IS 'Унікальний ідентифікатор користувача';
COMMENT ON COLUMN users.login IS 'Номер телефону користувача';
COMMENT ON COLUMN users.password_hash IS 'Хеш пароля';
COMMENT ON COLUMN users.name IS 'Ім''я користувача';
COMMENT ON COLUMN users.payload IS 'Інформація користувача';
COMMENT ON COLUMN users.authorities IS 'Дозволи та ролі користувача';
COMMENT ON COLUMN users.created_at IS 'Дата та час створення користувача';
COMMENT ON COLUMN users.updated_at IS 'Дата та час останнього оновлення користувача';

CREATE TABLE vehicles
(
    id            BIGSERIAL PRIMARY KEY,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    make          VARCHAR(50) NOT NULL,
    model         VARCHAR(50) NOT NULL,
    color         VARCHAR(20),
    driver_id     BIGINT      NOT NULL,
    FOREIGN KEY (driver_id) REFERENCES users (id)
);

COMMENT ON TABLE vehicles IS 'Таблиця з інформацією про транспортні засоби';
COMMENT ON COLUMN vehicles.id IS 'Унікальний ідентифікатор транспортного засобу';
COMMENT ON COLUMN vehicles.license_plate IS 'Номерний знак транспортного засобу';
COMMENT ON COLUMN vehicles.make IS 'Марка транспортного засобу';
COMMENT ON COLUMN vehicles.model IS 'Модель транспортного засобу';
COMMENT ON COLUMN vehicles.color IS 'Колір транспортного засобу';
COMMENT ON COLUMN vehicles.driver_id IS 'Ідентифікатор водія, який володіє транспортним засобом';


CREATE TABLE bookings
(
    id               BIGSERIAL PRIMARY KEY,
    parking_space_id BIGINT      NOT NULL,
    vehicle_id       BIGINT      NOT NULL,
    start_time       TIMESTAMP   NOT NULL,
    end_time         TIMESTAMP   NOT NULL,
    status           VARCHAR(20) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parking_space_id) REFERENCES parking_spaces (id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

COMMENT ON TABLE bookings IS 'Таблиця з інформацією про бронювання паркувальних місць';
COMMENT ON COLUMN bookings.id IS 'Унікальний ідентифікатор бронювання';
COMMENT ON COLUMN bookings.parking_space_id IS 'Ідентифікатор паркувального місця';
COMMENT ON COLUMN bookings.vehicle_id IS 'Ідентифікатор водія, який забронював місце';
COMMENT ON COLUMN bookings.start_time IS 'Час початку бронювання';
COMMENT ON COLUMN bookings.end_time IS 'Час завершення бронювання';
COMMENT ON COLUMN bookings.status IS 'Статус бронювання (активне, завершене, скасоване)';
COMMENT ON COLUMN bookings.created_at IS 'Дата та час створення бронювання';
COMMENT ON COLUMN bookings.updated_at IS 'Дата та час останнього оновлення бронювання';