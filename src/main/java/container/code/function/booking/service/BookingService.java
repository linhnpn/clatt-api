package container.code.function.booking.service;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.BookingOrder;
import container.code.function.booking.api.BookingResponse;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    ResponseEntity<ResponseObject> addBookingOrder(Integer userId, Integer employeeId, Integer jobId, LocalDateTime timestamp,
                                                   Integer address_id, String status, String description, Integer workTime);

    ResponseEntity<ResponseObject> updateBookingOrder(Integer bookingOrderId, BookingOrder bookingOrder);

    ResponseEntity<ResponseObject> deleteBookingOrder(Integer bookingOrderId);

    ResponseEntity<ResponseObject> getBookingOrder(String status, Integer userId, Integer employeeId, Integer bookingId);
}
