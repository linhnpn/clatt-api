package container.code.function.booking.controller;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.BookingOrder;
import container.code.function.booking.service.BookingService;
import container.code.function.booking.api.BookingResponse;
import container.code.function.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping(value = "/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/get-bookings")
    public ResponseEntity<ResponseObject> getBooking(@RequestParam(required = false) String status,
                                                     @RequestParam(required = false) Integer user_id,
                                                     @RequestParam(required = false) Integer employee_id,
                                                     @RequestParam(required = false) Integer booking_id) {
        return bookingService.getBookingOrder(status, user_id, employee_id, booking_id);
    }

    @PostMapping("/create-booking")
    public ResponseEntity<ResponseObject> createBooking(@RequestParam Integer userId, @RequestParam Integer employeeId,
                                                        @RequestParam Integer jobId, @RequestParam String timestamp,
                                                        @RequestParam Integer address_id, @RequestParam String status,
                                                        @RequestParam String description, @RequestParam Integer workTime) {
        LocalDateTime dateTime = LocalDateTime.parse(timestamp);
        return bookingService.addBookingOrder(userId, employeeId, jobId, dateTime, address_id, status, description, workTime);
    }

    @PutMapping("/update/{booking_id}")
    public ResponseEntity<ResponseObject> updateBooking(@PathVariable("booking_id") int booking_id,
                                                        @RequestBody(required = false) BookingOrder bookingOrder) {
        return bookingService.updateBookingOrder(booking_id, bookingOrder);
    }

    @DeleteMapping("/delete/{booking_id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable("booking_id") int booking_id) {
        return bookingService.deleteBookingOrder(booking_id);

    }
}