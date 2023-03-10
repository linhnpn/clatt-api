package container.code.function.order.controller;

import container.code.data.dto.ResponseObject;
import container.code.function.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderListEmployee")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<ResponseObject> getOrderListForEmployee(
            @RequestParam(name = "employee_id") Integer id) {
        return orderService.getOrderListForEmployee(id);
    }

    @GetMapping("/getOrderListFilter")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<ResponseObject> getWaitingOrderList(
            @RequestParam(name = "employee_id") Integer id,
            @RequestParam(name = "status") String status) {
        return orderService.getOrderListFilter(id, status);
    }

    @GetMapping("/getOrderListRenter")
    @PreAuthorize("hasAuthority('renter')")
    public ResponseEntity<ResponseObject> getOrderListForRenter(
            @RequestParam(name = "renter_id") Integer id) {
        return orderService.getOrderListForRenter(id);
    }

    @PutMapping("updateOrderStatus")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<ResponseObject> updateOrderStatus (
            @RequestParam(name = "order_id") Integer id,
            @RequestParam(name = "status") String status) {
        return orderService.updateStatus(id, status);
    }
}
