package container.code.function.feedback.controller;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Feedback;
import container.code.function.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("getFeedback")
    public ResponseEntity<ResponseObject> getFeedback(
            @RequestParam(name = "account_id") Integer id) {
        return feedbackService.getFeedback(id);
    }

    @GetMapping("getFeedbackWithRate")
    public ResponseEntity<ResponseObject> getFeedbackWithRate(
            @RequestParam(name = "account_id") Integer id,
            @RequestParam(name = "rate") Integer rate) {
        return feedbackService.getFeedbackWithRate(id, rate);
    }

    @PostMapping("/get-feedbacks")
    public ResponseEntity<ResponseObject> getFeedback(@RequestParam int employee_id, @RequestParam int job_id, @RequestParam(required = false) Integer rate) {
        return feedbackService.getFeedbacks(employee_id, job_id, rate);
    }

    @PostMapping("/create-feedback/{id}")
    public ResponseEntity<ResponseObject> createFeedback(@PathVariable("order_id") Integer orderId, @RequestBody Feedback feedback) {
        try {
            feedback.setId(null);
            return feedbackService.addFeedback(orderId, feedback);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<ResponseObject> updateFeedback(@PathVariable("feedbackId") int feedbackId, @RequestBody Feedback feedback) {
        try {
            feedback.setId(feedbackId);
            return feedbackService.updateFeedback(feedback);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity deleteFeedback(@PathVariable("feedbackId") int feedbackId) {
        try {

            Feedback feedback = new Feedback();
            feedback.setId(feedbackId);
            return feedbackService.deleteFeedback(feedback);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}