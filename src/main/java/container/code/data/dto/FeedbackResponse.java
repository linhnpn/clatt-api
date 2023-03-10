package container.code.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Integer feedbackId;
    private String renterName;
    private String profilePicture;
    private String detail;
    private Integer rate;
    private LocalDateTime timestamp;
}
