package container.code.function.district.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictResponse {
    private Integer district_id;
    private String districtName;

}
