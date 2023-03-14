package container.code.function.province;

import container.code.data.entity.Province;
import container.code.function.province.api.ProvinceResponse;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {
    public ProvinceResponse toProvinceResponse(Province province) {
        ProvinceResponse provinceResponse = new ProvinceResponse();
        provinceResponse.setProvince_id(province.getId());
        provinceResponse.setProvinceName(province.getName());
        return provinceResponse;
    }
}
