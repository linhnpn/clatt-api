package container.code.function.address.service;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Address;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<ResponseObject> getAllAddress();

    ResponseEntity<ResponseObject> getAddressesByAccount(int accountId);

    ResponseEntity<ResponseObject> findAddressByAccountId(int accountId);

    ResponseEntity<ResponseObject> addAddress(Address address);

    ResponseEntity<ResponseObject> updateAddress(Address address);

    ResponseEntity<ResponseObject> deleteAddress(Address address);
}
