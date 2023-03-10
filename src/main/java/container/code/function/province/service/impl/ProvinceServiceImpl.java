package container.code.function.province.service.impl;

import container.code.data.entity.Province;
import container.code.data.repository.ProvinceRepository;
import container.code.function.province.ProvinceMapper;
import container.code.function.province.api.ProvinceResponse;
import container.code.function.province.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Override
    public Optional<Province> getProvinceById(Integer Id) { return provinceRepository.findById(Id);}

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public List<ProvinceResponse> getAllProvince() {
        return provinceRepository.findAll()
                .stream()
                .map(provinceMapper::toProvinceResponse)
                .collect(Collectors.toList());
    }
}
