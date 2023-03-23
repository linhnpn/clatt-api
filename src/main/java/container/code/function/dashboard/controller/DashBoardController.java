package container.code.function.dashboard.controller;

import container.code.data.dto.ResponseObject;
import container.code.function.dashboard.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = {"http://localhost:3030", "https://clatt-api.monoinfinity.net",
        "https://cleaning-house-service.vercel.app", "http://localhost:8080"}, allowCredentials = "true")
@RequestMapping(value = "/dashboard")
public class DashBoardController {
    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getDashboard(@RequestParam(value = "status", required = false) String status,
                                                    @RequestParam("endDate") String year) {
        try {
            LocalDateTime yearTime = LocalDateTime.parse(year);
            return dashBoardService.getIncomeBaseDate(status, yearTime);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-total-user")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getTotalUser() {
        try {
            return dashBoardService.getTotalUser();
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-total-employee")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getTotalEmployee() {
        try {
            return dashBoardService.getTotalEmployee();
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-total-job")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getTotalJob() {
        try {
            return dashBoardService.getTotalJob();
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-total-income")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> getTotalIncome(@RequestParam(value = "status", required = false) String status,
                                                    @RequestParam("endDate") String year) {
        try {
            LocalDateTime yearTime = LocalDateTime.parse(year);
            return dashBoardService.getTotalIncome(status, yearTime);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
