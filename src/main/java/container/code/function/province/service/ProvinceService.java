package container.code.function.province.service;

import container.code.data.entity.Province;
import container.code.function.province.api.ProvinceResponse;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    Optional<Province> getProvinceById(Integer Id);

    List<ProvinceResponse> getAllProvince();
}
