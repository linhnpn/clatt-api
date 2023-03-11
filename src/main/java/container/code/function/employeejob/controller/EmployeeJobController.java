package container.code.function.employeejob.controller;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Feedback;
import container.code.function.employeejob.service.EmployeeJobService;
import container.code.function.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping("/worker")
public class EmployeeJobController {
    @Autowired
    private EmployeeJobService employeeJobService;
    @GetMapping("/{jobId}")
    public ResponseEntity<ResponseObject> getWorker(@PathVariable("jobId") int jobId) {
        try {
            return employeeJobService.getWorkerByJobId(jobId);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
