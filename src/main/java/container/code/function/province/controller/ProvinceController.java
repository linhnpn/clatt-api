package container.code.function.province.controller;

import container.code.data.entity.Account;
import container.code.data.entity.Province;
import container.code.function.province.api.ProvinceResponse;
import container.code.function.province.service.ProvinceService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping(value = "/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("getProvinceById")
    public Optional<Province> getProvinceById(
            @Parameter(description = "Enter province Id") @RequestParam(name = "id") @Min(value = 1, message = "Id must be equal or greater than 1") Integer Id
    ) { return provinceService.getProvinceById(Id);}

    @GetMapping("/all")
    public ResponseEntity<List<ProvinceResponse>> getAll(){
        return new ResponseEntity<List<ProvinceResponse>>(provinceService.getAllProvince(), HttpStatus.OK);
    }
}