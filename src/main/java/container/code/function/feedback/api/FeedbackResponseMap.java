package container.code.function.feedback.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponseMap {
    private Integer id;
    private String detail;
    private Integer rate;
    private Date timestamp;
    private Integer user_id;
    private String userName;
    private Integer employee_id;

}