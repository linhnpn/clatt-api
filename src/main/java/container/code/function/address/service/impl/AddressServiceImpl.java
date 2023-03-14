package container.code.function.address.service.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Address;
import container.code.data.repository.AddressRepository;
import container.code.function.address.AddressMapper;
import container.code.function.address.service.AddressService;
import container.code.function.address.api.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public ResponseEntity<ResponseObject> findAddressByAccountId(int accountId) {
        try {
            AddressResponse address = addressMapper.toAddressResponse(addressRepository.findAddressByAccountId(accountId));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAllAddress() {
        try {
            List<AddressResponse> list = addressRepository.findAll()
                    .stream()
                    .map(addressMapper::toAddressResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, list));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAddressesByAccount(int accountId) {
        try {
            List<Address> list = addressRepository.getAddressesByAccount(accountId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> addAddress(Address address) {
        try {
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Create Address Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateAddress(Address address) {
        try {
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Update Address Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteAddress(Address address) {
        try {
            addressRepository.delete(address);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Deleted Address Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }
}
