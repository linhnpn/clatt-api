package container.code.function.feedback.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponseMap {
    private Integer id;
    private String detail;
    private Integer rate;
    private String profilePicture;
    private LocalDateTime timestamp;
    private Integer user_id;
    private String userName;
    private Integer employee_id;

}