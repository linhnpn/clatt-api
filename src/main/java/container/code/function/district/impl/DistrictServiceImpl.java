package container.code.function.district.impl;

import container.code.data.dto.DistrictDto;
import container.code.data.dto.ResponseObject;
import container.code.data.repository.DistrictRepository;
import container.code.function.district.DistrictMapper;
import container.code.function.district.DistrictService;
import container.code.function.district.api.DistrictResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private DistrictMapper districtMapper;

    private static final String HASH_KEY = "DISTRICT";
    private final RedisTemplate redisTemplate;

    public DistrictServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public ResponseEntity<ResponseObject> getAllDistrict(Integer provinceId) {
        try {
            List<DistrictResponse> list = districtRepository.findAllByProvinceId(provinceId)
                    .stream()
                    .map(districtMapper::toDistrictResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public boolean saveDistrict(DistrictDto district) {
            String districtId = String.valueOf(district.getId());
            try {
                redisTemplate.opsForHash().put(HASH_KEY, districtId, district);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
    }

    @Override
    public List<DistrictDto> fetchAllDistrict() {
        List<DistrictDto> districts;
        districts = redisTemplate.opsForHash().values(HASH_KEY);
        return districts;
    }
}
