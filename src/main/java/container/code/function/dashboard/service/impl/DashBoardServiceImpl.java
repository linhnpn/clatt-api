package container.code.function.dashboard.service.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Job;
import container.code.data.repository.AccountRepository;
import container.code.data.repository.BookingOrderRepository;
import container.code.data.repository.JobRepository;
import container.code.function.dashboard.response.DashBoardMapper;
import container.code.function.dashboard.response.DashBoardResponse;
import container.code.function.dashboard.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    BookingOrderRepository bookingOrderRepository;
    @Autowired
    DashBoardMapper dashBoardMapper;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    AccountRepository accountRepository;
    @Override
    public ResponseEntity<ResponseObject> getTotalUser() {
        try {
            Integer total = accountRepository.getTotalAccount("renter");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, total));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getTotalEmployee() {
        try {
            Integer total = accountRepository.getTotalAccount("employee");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, total));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getIncomeBaseDate(String status, LocalDateTime year) {
        try {
            List<DashBoardResponse> dashBoardResponses = bookingOrderRepository.getDashboard(status, year).stream()
                    .map(dashBoardMapper::toDashBoardResponse)
                    .collect(Collectors.toList());
            int size = dashBoardResponses.size();
            for (int i = 1; i <= 12; i++) {
                for (int j = 0; j <= size; j++) {
                    if (dashBoardResponses.get(j).getMonth() != i) {
                        dashBoardResponses.add(new DashBoardResponse(0L,0L, i));
                    }
                    break;
                }
            }
            Collections.sort(dashBoardResponses, Comparator.comparing(DashBoardResponse::getMonth));


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, dashBoardResponses));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getTotalJob() {
        try {
            Integer total = jobRepository.getAllJob();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, total));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getTotalIncome(String status, LocalDateTime year) {
        try {
            Integer total = bookingOrderRepository.getTotalIncome(status, year);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, total));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur", null));
        }
    }
}
