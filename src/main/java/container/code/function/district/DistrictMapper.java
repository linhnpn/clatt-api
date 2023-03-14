package container.code.function.district;

import container.code.data.entity.District;
import container.code.function.district.api.DistrictResponse;
import org.springframework.stereotype.Component;

@Component
public class DistrictMapper {
    public DistrictResponse toDistrictResponse(District district) {
        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setDistrict_id(district.getId());
        districtResponse.setDistrictName(district.getName());
        return districtResponse;
    }
}
