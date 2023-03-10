package container.code.function.feedback.service;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Feedback;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    ResponseEntity<ResponseObject> getFeedback(Integer id);

    ResponseEntity<ResponseObject> getFeedbackWithRate(Integer id, Integer rate);

    ResponseEntity<ResponseObject> getFeedbacks(int employee_id, int job_id, Integer rate);

    ResponseEntity<ResponseObject> addFeedback(Integer id, Feedback feedback) throws IllegalAccessException;

    ResponseEntity<ResponseObject> updateFeedback(Feedback feedback);

    ResponseEntity<ResponseObject> deleteFeedback(Feedback feedback);

}
