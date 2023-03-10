package container.code.function.order.service.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.BookingOrder;
import container.code.data.repository.BookingOrderRepository;
import container.code.function.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BookingOrderRepository bookingOrderRepository;
    @Override
    public ResponseEntity<ResponseObject> getOrderListForEmployee (Integer id) {
        try {
            List<BookingOrder> list = bookingOrderRepository.findByEmployeeId(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject(HttpStatus.FOUND.toString(), null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getOrderListForRenter (Integer id) {
        try {
            List<BookingOrder> list = bookingOrderRepository.findByRenterId(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject(HttpStatus.FOUND.toString(), null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatus(Integer id, String status) {
        try {
            String orderStatus = bookingOrderRepository.findStatusById(id);
            if ((orderStatus.equals("waiting") && (status.equals("confirmed") || status.equals("canceled"))) ||
                (orderStatus.equals("confirmed") && (status.equals("finished") || status.equals("canceled")))) {
                bookingOrderRepository.updateStatus(id, status);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Can not update status!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getOrderListFilter(Integer id, String status) {
        try {
            List<BookingOrder> list = bookingOrderRepository.findByEmployeeIdAndStatus(id, status);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(),null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }
}
