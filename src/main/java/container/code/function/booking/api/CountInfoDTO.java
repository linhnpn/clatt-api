package container.code.function.booking.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountInfoDTO {
    long userCount;

    long feedbackCount;

    long confirmedOrderCount;

    long finishedOrderCount;
}
