package container.code.function.order.service;

import container.code.data.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<ResponseObject> getOrderListForEmployee(Integer id);

    ResponseEntity<ResponseObject> getOrderListForRenter(Integer id);

    ResponseEntity<ResponseObject> updateStatus(Integer id,String status);

    ResponseEntity<ResponseObject> getOrderListFilter(Integer id,String status);


}
