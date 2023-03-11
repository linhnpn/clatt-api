package container.code.function.booking.service.impl;

import container.code.data.dto.NotificationRequestDto;
import container.code.data.dto.ResponseObject;
import container.code.data.entity.*;
import container.code.data.repository.*;
import container.code.function.account.service.notification.FCMService;
import container.code.function.booking.BookingMapper;
import container.code.function.booking.service.BookingService;
import container.code.function.booking.api.BookingResponse;
import container.code.function.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingOrderRepository bookingOrderRepository;
    @Autowired
    OrderJobRepository orderJobRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FCMService fcmService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    NotificationService notificationService;

    private BookingOrder findBooking(Integer id) {
        BookingOrder existBooking = bookingOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));
        return existBooking;
    }

    private Account findAccount(Integer acc_id) {
        Account existAcc = accountRepository.findById(acc_id).orElseThrow(() -> new NotFoundException("Account not found"));
        return existAcc;
    }

    private Job findJob(Integer job_id) {
        Job existJob = jobRepository.findById(job_id).orElseThrow(() -> new NotFoundException("Job not found"));
        return existJob;
    }

    @Override
    public ResponseEntity<ResponseObject> addBookingOrder(Integer userId, Integer employeeId, Integer jobId, LocalDateTime timestamp,
                                                          Integer address_id, String status, String description, Integer workTime) {
        try {
            BookingOrder bookingOrder = new BookingOrder();
            Account accountUser = findAccount(userId);
            Account accountEmp = findAccount(employeeId);
            bookingOrder.setWorkHour(workTime);
            bookingOrder.setRenter(accountUser);
            bookingOrder.setStatus(status);
            bookingOrder.setTimestamp(timestamp);
            bookingOrder.setDescription(description);
            bookingOrder.setEmployee(accountEmp);
            bookingOrder.setLocation("903 D1, Thu Duc City, Hồ Chí Minh City");
            BookingOrder saveBooking = bookingOrderRepository.save(bookingOrder);


            Job job = findJob(jobId);

            OrderJob orderJob = new OrderJob();
            orderJob.setBookingOrder(saveBooking);
            orderJob.setJob(job);
            orderJobRepository.save(orderJob);
            NotificationRequestDto dto = new NotificationRequestDto();
            dto.setTarget(accountUser.getFcmToken());
            dto.setBody("Booking has been booked, please wait for confirmation");
            dto.setTitle("Booking Success!");
            fcmService.sendPnsToTopic(dto);
            NotificationRequestDto dtoForEmp = new NotificationRequestDto();
            dtoForEmp.setTarget(accountEmp.getFcmToken());
            dtoForEmp.setBody("An booking for you, please  confirm");
            dtoForEmp.setTitle("New Booking!");
            fcmService.sendPnsToTopic(dtoForEmp);
            notificationService.createNotification(accountEmp.getId(), status, dto.getBody());
            notificationService.createNotification(accountUser.getId(), status, dtoForEmp.getBody());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Add new Booking Successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }


    }

    @Override
    public ResponseEntity<ResponseObject> updateBookingOrder(Integer bookingOrderId, BookingOrder bookingOrder) {
        try {
            BookingOrder existBookingOrder = findBooking(bookingOrderId);
            existBookingOrder.setStatus(bookingOrder.getStatus());
            existBookingOrder.setWorkHour(bookingOrder.getWorkHour());
            existBookingOrder.setDescription(bookingOrder.getDescription());
            Account accountUser = findAccount(existBookingOrder.getRenter().getId());
            Account accountEmp = findAccount(existBookingOrder.getEmployee().getId());
            NotificationRequestDto dto = new NotificationRequestDto();
            NotificationRequestDto dtoForEmp = new NotificationRequestDto();
            if (bookingOrder.getStatus().equals("done")) {
                dto.setTarget(accountUser.getFcmToken());
                dto.setBody("It done, pls vote and feedback");
                dto.setTitle("All Done!");
                fcmService.sendPnsToTopic(dto);
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                dtoForEmp.setBody("All Done, Good Job!!");
                dtoForEmp.setTitle("Done!");
                fcmService.sendPnsToTopic(dtoForEmp);
            } else if (bookingOrder.getStatus().equals("undone")) {
                dto.setTarget(accountUser.getFcmToken());
                dto.setBody("Employee has been cofirm your booking");
                dto.setTitle("Waiting for Employee!");
                fcmService.sendPnsToTopic(dto);
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                dtoForEmp.setBody("Confirm success, work on time!!");
                dtoForEmp.setTitle("Confirm!");
                fcmService.sendPnsToTopic(dtoForEmp);
            } else if (bookingOrder.getStatus().equals("cancel")) {
                dto.setTarget(accountUser.getFcmToken());
                dto.setBody("Booking is cancel Successful!");
                dto.setTitle("Cancel!");
                fcmService.sendPnsToTopic(dto);
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                dtoForEmp.setBody("Booking" + bookingOrderId + " is cancel!");
                dtoForEmp.setTitle("Cancel!");
                fcmService.sendPnsToTopic(dtoForEmp);
            }
            notificationService.createNotification(accountEmp.getId(), bookingOrder.getStatus(), dto.getBody());
            notificationService.createNotification(accountUser.getId(), bookingOrder.getStatus(), dtoForEmp.getBody());
            bookingOrderRepository.save(existBookingOrder);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Updated Booking Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> deleteBookingOrder(Integer bookingOrderId) {
        try {
            BookingOrder existBookingOrder = findBooking(bookingOrderId);
            existBookingOrder.setStatus("Deleted");
            bookingOrderRepository.save(existBookingOrder);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Deleted Booking Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getBookingOrder(String status, Integer userId, Integer employeeId, Integer bookingId) {
        try {
            List<BookingResponse> bookingResponses = bookingOrderRepository.findAllByStatusId(status, userId, employeeId, bookingId)
                    .stream().map(bookingMapper::toBookingResponse).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, bookingResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }
}
