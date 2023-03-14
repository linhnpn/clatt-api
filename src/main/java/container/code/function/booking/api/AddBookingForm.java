package container.code.function.booking.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddBookingForm {

    private Integer renterId;

    private Integer employeeId;

    private Integer workHour;

    private String location;

    private String description;

    private LocalDateTime workDate;

    private LocalDateTime timestamp;

    private Integer jobId;
}
