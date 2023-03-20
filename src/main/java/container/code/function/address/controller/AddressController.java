package container.code.function.address.controller;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Address;
import container.code.function.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3030", "https://clatt-api.monoinfinity.net",
        "https://cleaning-house-service.vercel.app", "http://localhost:8080"}, allowCredentials = "true")
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<ResponseObject> getAllAddresses() {
        return addressService.getAllAddress();
    }

    @GetMapping("account/{accountId}")
    @PreAuthorize("hasAnyAuthority('admin', 'renter', 'employee')")
    public ResponseEntity<ResponseObject> getAddressByAccountId(@PathVariable("accountId") int accountId) {
        return addressService.findAddressByAccountId(accountId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin', 'renter', 'employee')")
    public ResponseEntity<ResponseObject> createAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    @PutMapping("{addressId}")
    @PreAuthorize("hasAnyAuthority('admin', 'renter', 'employee')")
    public ResponseEntity<ResponseObject> updateAddress(@PathVariable("addressId") int addressId, @RequestBody Address address) {
        address.setId(addressId);
        return addressService.updateAddress(address);
    }
}
