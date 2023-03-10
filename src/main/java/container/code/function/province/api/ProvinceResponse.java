package container.code.function.province.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceResponse {
    private Integer province_id;
    private String provinceName;
}
