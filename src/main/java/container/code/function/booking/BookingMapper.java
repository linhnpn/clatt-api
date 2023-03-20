package container.code.function.booking;

import container.code.function.booking.api.BookingResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class BookingMapper {
    public BookingResponse toBookingResponse(Map<String, Object> object) {
        BookingResponse bookingResponse = new BookingResponse();

        bookingResponse.setId((Integer) object.get("id"));
        bookingResponse.setUserId((Integer) object.get("user_id"));
        bookingResponse.setUserName((String) object.get("user_name"));
        bookingResponse.setEmpId((Integer) object.get("emp_id"));
        bookingResponse.setEmpName((String) object.get("emp_name"));
        bookingResponse.setStatus((String) object.get("status"));
        bookingResponse.setWorkTime((Integer) object.get("workTime"));
        bookingResponse.setTimestamp((LocalDateTime) object.get("timestamp"));
        bookingResponse.setLocation((String) object.get("location"));
        bookingResponse.setJobId((Integer) object.get("job_id"));
        bookingResponse.setJobName((String) object.get("job_name"));
        bookingResponse.setDescription((String) object.get("description"));
        bookingResponse.setJobImage((String) object.get("jobImage"));
        bookingResponse.setPrice((Integer) object.get("price") * bookingResponse.getWorkTime());
        return bookingResponse;
    }
}
