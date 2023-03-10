package container.code.function.feedback;

import container.code.data.entity.Feedback;
import container.code.function.feedback.api.FeedbackResponseMap;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public FeedbackResponseMap toFeedbackResponse(Feedback feedback) {
        FeedbackResponseMap feedbackResponseMap = new FeedbackResponseMap();
        feedbackResponseMap.setDetail(feedback.getDetail());
        feedbackResponseMap.setRate(feedback.getRate());
//        feedbackResponse.setTimestamp(feedback.getTimestamp());
//        feedbackResponse.setUser_id(feedback.getEmployeeOrder().getBookingOrder().getAccount().getId());
//        feedbackResponse.setUserName(feedback.getEmployeeOrder().getBookingOrder().getAccount().getFullname());
//        feedbackResponse.setEmployee_id(feedback.getEmployeeOrder().getAccount().getId());
        return feedbackResponseMap;
    }
}