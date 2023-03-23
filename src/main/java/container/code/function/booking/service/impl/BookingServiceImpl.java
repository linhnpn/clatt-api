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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    AddressRepository addressRepository;
    @Autowired
    NotificationStatusRepository notificationStatusRepository;

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

    private NotificationStatus findCommonNotification(Integer statusId) {
        NotificationStatus notificationStatus = notificationStatusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("NotificationStatus not found"));
        return notificationStatus;
    }

    private List<BookingOrder> checkBooking(LocalDateTime start, Integer id) {
        List<BookingOrder> existBooking = bookingOrderRepository.findByDateTime(start, id);
        return existBooking;
    }

    @Override
    public ResponseEntity<ResponseObject> addBookingOrder(Integer renterId, Integer employeeId, Integer jobId, LocalDateTime timestamp,
                                                          LocalDateTime workDate,
                                                          String address_id, String status, String description, Integer workTime) {
        try {
            BookingOrder bookingOrder = new BookingOrder();
            Account accountUser = findAccount(renterId);
            Account accountEmp = findAccount(employeeId);
            boolean check = accountUser.getAddress().getDistrictId() == accountEmp.getAddress().getDistrictId();
            if (check) {
                bookingOrder.setWorkHour(workTime);
                bookingOrder.setRenter(accountUser);
                bookingOrder.setEmployee(accountEmp);
                bookingOrder.setStatus(status);
                bookingOrder.setWorkDate(workDate);
                bookingOrder.setTimestamp(timestamp);
                bookingOrder.setDescription(description);
                bookingOrder.setLocation(address_id);
                BookingOrder saveBooking = bookingOrderRepository.save(bookingOrder);
                Job job = findJob(jobId);

                OrderJob orderJob = new OrderJob();
                orderJob.setBookingOrder(saveBooking);
                orderJob.setJob(job);
                orderJobRepository.save(orderJob);

                //Push Notification for Renter
                NotificationRequestDto dto = new NotificationRequestDto();
                dto.setTarget(accountUser.getFcmToken());
                String notiForRenter = String.format(findCommonNotification(1).getCommonMessage(),
                        findJob(jobId).getName(), accountEmp.getFullname());
                dto.setBody(notiForRenter);
                dto.setTitle("Booking Success!");
                fcmService.sendPnsToTopic(dto);

                //Push notification for Employee
                NotificationRequestDto dtoForEmp = new NotificationRequestDto();
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                String notiForEmpl = String.format(findCommonNotification(5).getCommonMessage(), findJob(jobId).getName());
                dtoForEmp.setBody(notiForEmpl);
                dtoForEmp.setTitle("New Booking!");
                fcmService.sendPnsToTopic(dtoForEmp);

                notificationService.createNotification(accountUser.getId(), status, dto.getBody(), LocalDateTime.now());
                notificationService.createNotification(accountEmp.getId(), status, dtoForEmp.getBody(), LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Add new Booking Successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject(HttpStatus.CONFLICT.toString(), "Can't book staff who are too far away", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateBookingOrder(Integer bookingOrderId, BookingOrder bookingOrder) {
        try {
            BookingOrder existBookingOrder = findBooking(bookingOrderId);
            existBookingOrder.setStatus(existBookingOrder.getStatus());
            Account accountUser = existBookingOrder.getRenter();
            Account accountEmp = existBookingOrder.getEmployee();
            NotificationRequestDto dto = new NotificationRequestDto();
            NotificationRequestDto dtoForEmp = new NotificationRequestDto();
            if (existBookingOrder.getStatus().equals("done")) {
                dto.setTarget(accountUser.getFcmToken());
                dto.setBody("It done, pls vote and feedback");
                dto.setTitle("All Done!");
                fcmService.sendPnsToTopic(dto);
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                dtoForEmp.setBody("All Done, Good Job!!");
                dtoForEmp.setTitle("Done!");
                fcmService.sendPnsToTopic(dtoForEmp);
            } else if (existBookingOrder.getStatus().equals("undone")) {
                dto.setTarget(accountUser.getFcmToken());
                dto.setBody("Employee has been confirm your booking");
                dto.setTitle("Waiting for Employee!");
                fcmService.sendPnsToTopic(dto);
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                dtoForEmp.setBody("Confirm success, work on time!!");
                dtoForEmp.setTitle("Confirm!");
                fcmService.sendPnsToTopic(dtoForEmp);
            } else if (existBookingOrder.getStatus().equals("cancel")) {
                dto.setTarget(accountUser.getFcmToken());
                dto.setBody("Booking is cancel Successful!");
                dto.setTitle("Cancel!");
                fcmService.sendPnsToTopic(dto);
                dtoForEmp.setTarget(accountEmp.getFcmToken());
                dtoForEmp.setBody("Booking" + bookingOrderId + " is cancel!");
                dtoForEmp.setTitle("Cancel!");
                fcmService.sendPnsToTopic(dtoForEmp);
            }
            notificationService.createNotification(accountEmp.getId(), existBookingOrder.getStatus(), dto.getBody(), LocalDateTime.now());
            notificationService.createNotification(accountUser.getId(), existBookingOrder.getStatus(), dtoForEmp.getBody(), LocalDateTime.now());
            bookingOrderRepository.save(existBookingOrder);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Updated Booking Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateBookingOrderStatus(Integer bookingOrderId, String status) {
        try {
            boolean check = false;
            BookingOrder existBookingOrder = findBooking(bookingOrderId);
            existBookingOrder.setStatus(status);
            Account accountUser = existBookingOrder.getRenter();
            Account accountEmp = existBookingOrder.getEmployee();

            if (existBookingOrder.getStatus().equals("done")) {
                if (existBookingOrder.getWorkDate().plusHours(existBookingOrder.getWorkHour()).isBefore(LocalDateTime.now())) {
                    String titleUser = "Success!";
                    String bodyUser = String.format(findCommonNotification(3).getCommonMessage(),
                            bookingOrderId, existBookingOrder.getOrderJobs().get(0).getJob().getName());
                    String titleEmployee = "Success!";
                    String bodyEmployee = String.format(findCommonNotification(7).getCommonMessage(),
                            existBookingOrder.getOrderJobs().get(0).getJob().getName(),
                            bookingOrderId, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                    pushNotification(accountEmp.getId(),
                            accountUser.getId(), titleUser, bodyUser,
                            titleEmployee, bodyEmployee,
                            accountUser.getFcmToken(),
                            accountEmp.getFcmToken(), existBookingOrder);
                } else {
                    return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(new ResponseObject(HttpStatus.FAILED_DEPENDENCY.toString(), "Cannot done before workDate!", null));
                }

            } else if (existBookingOrder.getStatus().equals("undone")) {
                LocalDateTime workEnd = existBookingOrder.getWorkDate().plusHours(existBookingOrder.getWorkHour());
                List<BookingOrder> bookingOrder = checkBooking(existBookingOrder.getWorkDate(), accountEmp.getId());
                if (!bookingOrder.isEmpty()) {
                    for (BookingOrder element :
                            bookingOrder) {
                        check = existBookingOrder.getWorkDate().isBefore(element.getWorkDate().plusHours(element.getWorkHour()).minusSeconds(1))
                                && workEnd.isAfter(element.getWorkDate().minusSeconds(1));
                        if (check) break;
                    }
                }
                String titleUser = "Upcoming!";
                String bodyUser = String.format(findCommonNotification(2).getCommonMessage(),
                        bookingOrderId, existBookingOrder.getOrderJobs().get(0).getJob().getName(),
                        accountEmp.getFullname());

                String titleEmployee = "Upcoming!";
                String bodyEmployee = String.format(findCommonNotification(6).getCommonMessage(),
                        bookingOrderId,
                        existBookingOrder.getOrderJobs().get(0).getJob().getName(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                        existBookingOrder.getLocation());

                pushNotification(accountEmp.getId(),
                        accountUser.getId(),
                        titleUser, bodyUser,
                        titleEmployee, bodyEmployee,
                        accountUser.getFcmToken(),
                        accountEmp.getFcmToken(),
                        existBookingOrder);

            } else if (existBookingOrder.getStatus().equals("cancel")) {

                String titleUser = "Cancel!";
                String bodyUser = String.format(findCommonNotification(4).getCommonMessage(),
                        bookingOrderId,
                        existBookingOrder.getOrderJobs().get(0).getJob().getName(),
                        existBookingOrder.getEmployee().getFullname());

                String titleEmployee = "Cancel!";
                String bodyEmployee = String.format(findCommonNotification(8).getCommonMessage(),
                        existBookingOrder.getOrderJobs().get(0).getJob().getName(),
                        bookingOrderId);

                pushNotification(accountEmp.getId(),
                        accountUser.getId(),
                        titleUser, bodyUser,
                        titleEmployee, bodyEmployee,
                        accountUser.getFcmToken(),
                        accountEmp.getFcmToken(),
                        existBookingOrder);
            }
            if (check) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject(HttpStatus.CONFLICT.toString(), "There was one booking during this time period!", null));
            } else {
                bookingOrderRepository.save(existBookingOrder);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Updated Booking Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    private void pushNotification(Integer accountEmpId, Integer accountUserId, String titleUser, String bodyUser, String bodyTitle,
                                  String bodyEmp, String fcmTokenUser, String fcmTokenEmployee, BookingOrder bookingOrder) {
        NotificationRequestDto dtoUser = new NotificationRequestDto();
        NotificationRequestDto dtoForEmp = new NotificationRequestDto();
        //Push notification User
        dtoUser.setTarget(fcmTokenUser);
        dtoUser.setBody(bodyUser);
        dtoUser.setTitle(titleUser);
        fcmService.sendPnsToTopic(dtoUser);

        //Push notification Employee
        dtoForEmp.setTarget(fcmTokenEmployee);
        dtoForEmp.setBody(bodyEmp);
        dtoForEmp.setTitle(bodyTitle);
        fcmService.sendPnsToTopic(dtoForEmp);

        notificationService.createNotification(accountEmpId, bookingOrder.getStatus(), dtoUser.getBody(), LocalDateTime.now());
        notificationService.createNotification(accountUserId, bookingOrder.getStatus(), dtoForEmp.getBody(), LocalDateTime.now());
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
