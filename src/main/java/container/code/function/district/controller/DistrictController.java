package container.code.function.district.controller;

import container.code.data.dto.DistrictDto;
import container.code.data.dto.ResponseObject;
import container.code.function.district.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping(value = "/district")
public class DistrictController {
    @Autowired
    private DistrictService districtService;

    @GetMapping("/{province_id}")
    public ResponseEntity<ResponseObject> getAll(@PathVariable("province_id") int province_id) {
        return districtService.getAllDistrict(province_id);
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAll() {
        Integer province_id = null;
        return districtService.getAllDistrict(province_id);
    }

    @PostMapping("/redisDistrict")
    public ResponseEntity<String> saveBrand(@RequestBody DistrictDto district) {
        boolean result = districtService.saveDistrict(district);
        if (result)
            return ResponseEntity.ok("District Create Successfully");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/redisFetchAll")
    public ResponseEntity<List<DistrictDto>> fetchAllDistrict() {
        List<DistrictDto> districts;
        districts = districtService.fetchAllDistrict();
        return ResponseEntity.ok(districts);
    }
}
