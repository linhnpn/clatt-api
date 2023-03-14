package container.code.function.employeejob.api;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkerMapper {
    public WorkerResponse toEmpResponse(Map<String, Object> objectMap){
        WorkerResponse workerResponse = new WorkerResponse();
        workerResponse.setEmpId((Integer) objectMap.get("empId"));
        workerResponse.setJobEmpId((Integer) objectMap.get("jobEmpId"));
        workerResponse.setJobId((Integer) objectMap.get("jobId"));
        workerResponse.setEmpName((String) objectMap.get("empName"));
        workerResponse.setSrcPicture(objectMap.get("srcPicture") != null ? (String) objectMap.get("srcPicture") : "");
        workerResponse.setDescription(objectMap.get("description") != null ? (String) objectMap.get("description"): "Nothing to show");
        workerResponse.setCountRate(objectMap.get("countRate") != null ? (Long) objectMap.get("countRate") : 0);
        workerResponse.setAverageRate(objectMap.get("averageRate") != null ? (Double) objectMap.get("averageRate") : 5);
        workerResponse.setLocation((String) objectMap.get("location"));
        workerResponse.setJobName((String) objectMap.get("jobName"));
        workerResponse.setPriceJob((Integer) objectMap.get("priceJob"));
        return workerResponse;
    }
}
