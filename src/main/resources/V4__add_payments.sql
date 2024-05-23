CREATE TABLE payments
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT         NOT NULL,
    booking_id   BIGINT         NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP      NOT NULL,
    status       VARCHAR(20)    NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_booking FOREIGN KEY (booking_id) REFERENCES bookings (id)
);

COMMENT ON TABLE payments IS 'Таблиця з інформацією про оплати';
COMMENT ON COLUMN payments.id IS 'Унікальний ідентифікатор оплати';
COMMENT ON COLUMN payments.user_id IS 'Ідентифікатор користувача, який здійснив оплату';
COMMENT ON COLUMN payments.booking_id IS 'Ідентифікатор бронювання, за яке здійснена оплата';
COMMENT ON COLUMN payments.amount IS 'Сума оплати';
COMMENT ON COLUMN payments.payment_date IS 'Дата та час здійснення оплати';
COMMENT ON COLUMN payments.status IS 'Статус оплати (PAID, PENDING, CANCELLED)';
