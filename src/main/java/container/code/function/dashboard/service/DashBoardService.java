package container.code.function.dashboard.service;

import container.code.data.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface DashBoardService {
    ResponseEntity<ResponseObject> getTotalUser();
    ResponseEntity<ResponseObject> getTotalEmployee();
    ResponseEntity<ResponseObject> getIncomeBaseDate(String status, LocalDateTime end);
    ResponseEntity<ResponseObject> getTotalJob();
    ResponseEntity<ResponseObject> getTotalIncome(String status, LocalDateTime year);
}
