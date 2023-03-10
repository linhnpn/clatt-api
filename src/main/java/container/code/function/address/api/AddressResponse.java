package container.code.function.address.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {
    private Integer account_id;
    private String accountFullName;
    private String location;
    private Integer address_id;
}
