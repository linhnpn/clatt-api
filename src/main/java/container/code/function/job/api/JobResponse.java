package container.code.function.job.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse {
    private Integer id;
    private String jobName;
    private Integer price;
    private String thumbnailImage;
    private String measureUnit;

}
