package container.code.function.employeejob.service;

import container.code.data.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface EmployeeJobService {
    ResponseEntity<ResponseObject> getWorkerByJobId(Integer id);
}
