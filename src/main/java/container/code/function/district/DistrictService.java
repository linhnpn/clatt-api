package container.code.function.district;

import container.code.data.dto.DistrictDto;
import container.code.data.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DistrictService {
    ResponseEntity<ResponseObject> getAllDistrict(Integer provinceId);

    boolean saveDistrict(DistrictDto district);

    List<DistrictDto> fetchAllDistrict();
}
