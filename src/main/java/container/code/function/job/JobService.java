package container.code.function.job;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface JobService {
    ResponseEntity<ResponseObject> getAllJob();

    ResponseEntity<ResponseObject> getJob(Integer id);

    ResponseEntity<ResponseObject> addJob(Job job, MultipartFile file) throws IOException;

    ResponseEntity<ResponseObject> updateJob(Job job, MultipartFile file) throws IOException;

    ResponseEntity<ResponseObject> deleteJob(Job job);

    ResponseEntity<ResponseObject> registerJob(Integer jobId, Integer employeeId);

    ResponseEntity<ResponseObject> unregisterJob(Integer jobId, Integer employeeId);

    ResponseEntity<ResponseObject> getJobByEmp(Integer employeeId);
}
