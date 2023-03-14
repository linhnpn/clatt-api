package container.code.function.feedback;

import container.code.data.entity.Feedback;
import container.code.function.feedback.api.FeedbackResponseMap;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public FeedbackResponseMap toFeedbackResponse(Feedback feedback) {
        FeedbackResponseMap feedbackResponseMap = new FeedbackResponseMap();
        feedbackResponseMap.setId(feedback.getId());
        feedbackResponseMap.setDetail(feedback.getDetail());
        feedbackResponseMap.setRate(feedback.getRate());
        feedbackResponseMap.setProfilePicture(feedback.getBookingOrder().getRenter().getProfilePicture());
        feedbackResponseMap.setTimestamp(feedback.getTimestamp());
        feedbackResponseMap.setUser_id(feedback.getBookingOrder().getRenter().getId());
        feedbackResponseMap.setUserName(feedback.getBookingOrder().getRenter().getFullname());
        feedbackResponseMap.setEmployee_id(feedback.getBookingOrder().getEmployee().getId());
        return feedbackResponseMap;
    }
}