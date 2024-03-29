package container.code.function.employeejob.service.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.repository.EmployeeJobRepository;
import container.code.data.repository.FeedbackRepository;
import container.code.function.employeejob.api.WorkerMapper;
import container.code.function.employeejob.api.WorkerResponse;
import container.code.function.employeejob.service.EmployeeJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeJobServiceImpl implements EmployeeJobService {
    @Autowired
    private EmployeeJobRepository employeeJobRepository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    private WorkerMapper workerMapper;
    @Override
    public ResponseEntity<ResponseObject> getWorkerByJobId(Integer id) {
        try {
            List<WorkerResponse> workerResponses = employeeJobRepository.findAllByJob_Id(id).stream().map(workerMapper::toEmpResponse).collect(Collectors.toList());
            for (int i = 0; i < workerResponses.size(); i ++) {
                Long countFeedback = feedbackRepository.countFeedback(workerResponses.get(i).getJobId(), workerResponses.get(i).getEmpId());
                Double averageFeedback = feedbackRepository.averageFeedback(workerResponses.get(i).getJobId(), workerResponses.get(i).getEmpId());
                workerResponses.get(i).setCountRate(countFeedback);
                workerResponses.get(i).setAverageRate(averageFeedback);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, workerResponses));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }
}
