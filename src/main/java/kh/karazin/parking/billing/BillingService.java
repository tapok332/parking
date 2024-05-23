package kh.karazin.parking.billing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BillingService {

    private static final BigDecimal RATE_PER_MINUTE = new BigDecimal("2.00");

    /**
     * Розраховує суму заборгованості за перевищений час очікування.
     *
     * @param endTime     Час, коли бронювання повинно було завершитися.
     * @param currentTime Поточний час.
     * @return Сума заборгованості.
     */
    public static BigDecimal calculateOverdueAmount(LocalDateTime endTime, LocalDateTime currentTime) {
        if (currentTime.isBefore(endTime) || currentTime.isEqual(endTime)) {
            return BigDecimal.ZERO; // Якщо поточний час не перевищує кінцевий час, заборгованості немає.
        }

        return RATE_PER_MINUTE.multiply(new BigDecimal(Duration.between(endTime, currentTime).toMinutes()));
    }
}

