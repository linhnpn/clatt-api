package container.code.function.dashboard.response;

import container.code.data.entity.District;
import container.code.function.district.api.DistrictResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DashBoardMapper {
    public DashBoardResponse toDashBoardResponse(Map<String, Object> dashboard) {
        DashBoardResponse dashBoardResponse = new DashBoardResponse();
        dashBoardResponse.setCount((Long) dashboard.get("count"));
        dashBoardResponse.setSumPrice((Long) dashboard.get("sumPrice"));
        dashBoardResponse.setMonth((int) dashboard.get("month"));
        return dashBoardResponse;
    }
}
