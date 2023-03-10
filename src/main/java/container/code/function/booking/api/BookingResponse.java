package container.code.function.booking.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer empId;
    private String empName;
    private String status;
    private Integer workTime;
    private LocalDateTime timestamp;
    private Integer price;
    private String location;
    private Integer jobId;
    private String jobName;
    private String description;

}
