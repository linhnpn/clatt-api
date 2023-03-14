package container.code.function.booking.service;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.BookingOrder;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface BookingService {
    ResponseEntity<ResponseObject> addBookingOrder(Integer userId, Integer employeeId, Integer jobId, LocalDateTime timestamp,
                                                   LocalDateTime workDate,
                                                   Integer address_id, String status, String description, Integer workTime);

    ResponseEntity<ResponseObject> updateBookingOrder(Integer bookingOrderId, BookingOrder bookingOrder);

    ResponseEntity<ResponseObject> updateBookingOrderStatus(Integer bookingOrderId, String status);

    ResponseEntity<ResponseObject> deleteBookingOrder(Integer bookingOrderId);

    ResponseEntity<ResponseObject> getBookingOrder(String status, Integer userId, Integer employeeId, Integer bookingId);
}
