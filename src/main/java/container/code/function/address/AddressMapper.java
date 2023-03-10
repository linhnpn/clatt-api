package container.code.function.address;

import container.code.data.entity.Address;
import container.code.function.address.api.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponse toAddressResponse(Address address){
        AddressResponse addressResponse = new AddressResponse();
        String location = address.getDescription() + ", " + address.getDistrict().getName() + ", " + address.getDistrict().getProvince().getName();
        addressResponse.setAddress_id(address.getId());
        addressResponse.setAccount_id(address.getAccount().getId());
        addressResponse.setAccountFullName(address.getAccount().getFullname());
        addressResponse.setLocation(location);
        return addressResponse;
    }
}
