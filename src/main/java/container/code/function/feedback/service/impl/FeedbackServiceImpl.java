package container.code.function.feedback.service.impl;

import container.code.data.dto.FeedbackResponse;
import container.code.data.dto.ResponseObject;
import container.code.data.entity.BookingOrder;
import container.code.data.entity.Feedback;
import container.code.data.repository.BookingOrderRepository;
import container.code.data.repository.FeedbackRepository;
import container.code.function.feedback.FeedbackMapper;
import container.code.function.feedback.api.FeedbackResponseMap;
import container.code.function.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private BookingOrderRepository bookingOrderRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public ResponseEntity<ResponseObject> getFeedback(Integer id) {
        try {
            List<BookingOrder> list = bookingOrderRepository.findByEmployeeId(id);
            List<FeedbackResponse> responseList = new ArrayList<>();
            for (BookingOrder bookingOrder : list) {
                FeedbackResponse newOrder = new FeedbackResponse();
                newOrder.setFeedbackId(bookingOrder.getFeedback().getId());
                newOrder.setRenterName(bookingOrder.getRenter().getFullname());
                newOrder.setRate(bookingOrder.getFeedback().getRate());
                newOrder.setDetail(bookingOrder.getFeedback().getDetail());
                newOrder.setTimestamp(bookingOrder.getFeedback().getTimestamp());
                newOrder.setProfilePicture(bookingOrder.getRenter().getProfilePicture());
                responseList.add(newOrder);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, responseList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedbackWithRate(Integer id, Integer rate) {
        try {
            List<BookingOrder> list = bookingOrderRepository.findByEmployeeIdAndRate(id, rate);
            List<FeedbackResponse> responseList = new ArrayList<>();
            for (BookingOrder bookingOrder : list) {
                FeedbackResponse newOrder = new FeedbackResponse();
                newOrder.setFeedbackId(bookingOrder.getFeedback().getId());
                newOrder.setRenterName(bookingOrder.getRenter().getFullname());
                newOrder.setRate(bookingOrder.getFeedback().getRate());
                newOrder.setDetail(bookingOrder.getFeedback().getDetail());
                newOrder.setTimestamp(bookingOrder.getFeedback().getTimestamp());
                newOrder.setProfilePicture(bookingOrder.getRenter().getProfilePicture());
                responseList.add(newOrder);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, responseList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedbacks(int employee_id, int job_id, Integer rate) {
        try {
            List<FeedbackResponseMap> feedbacks = feedbackRepository.findAllByEmployeeOrder(employee_id, job_id, rate).stream().map(feedbackMapper::toFeedbackResponse).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, feedbacks));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    private Feedback findFeedback(Integer id) {
        Feedback existingFeedback = feedbackRepository.findById(id).orElseThrow(() -> new NotFoundException("Feedback not found"));
        return existingFeedback;
    }

    private BookingOrder findBookingOrder(Integer id) {
        BookingOrder bookingOrder = bookingOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee order not found"));
        return bookingOrder;
    }

    @Override
    public ResponseEntity<ResponseObject> addFeedback(Integer id, Feedback feedback) throws IllegalAccessException {
        try {
            BookingOrder bookingOrder = findBookingOrder(id);

            if (bookingOrder.getFeedback().getId() != null) {
                feedback.setBookingOrder(bookingOrder);
                feedbackRepository.save(feedback);
                bookingOrder.setFeedback(feedback);
                bookingOrderRepository.save(bookingOrder);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.CREATED.toString(), "Add a feedback successfully!", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Already have a feedback!", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    public ResponseEntity<ResponseObject> updateFeedback(Feedback feedback) {
        try {
            Feedback existingFeedback = findFeedback(feedback.getId());

            existingFeedback.setDetail(feedback.getDetail());
            existingFeedback.setRate(feedback.getRate());
            existingFeedback.setTimestamp(feedback.getTimestamp());

            feedbackRepository.save(existingFeedback);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.CREATED.toString(), "Update a feedback successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }

    }

    public ResponseEntity<ResponseObject> deleteFeedback(Feedback feedback) {
        try {
            Feedback existingFeedback = findFeedback(feedback.getId());
            BookingOrder bookingOrder = findBookingOrder(existingFeedback.getBookingOrder().getId());
            bookingOrder.setFeedback(null);
            bookingOrderRepository.save(bookingOrder);
            feedbackRepository.delete(existingFeedback);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Delete a feedback successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }
}
