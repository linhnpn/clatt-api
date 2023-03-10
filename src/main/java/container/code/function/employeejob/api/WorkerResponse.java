package container.code.function.employeejob.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerResponse {
    private Integer empId;
    private Integer jobEmpId;
    private String empName;
    private String srcPicture;
    private String description;
    private int countRate;
    private Double rate;
    private String location;
    private String jobName;
    private Integer priceJob;

}