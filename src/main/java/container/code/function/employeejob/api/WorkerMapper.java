package container.code.function.employeejob.api;

import container.code.data.entity.Account;
import container.code.data.entity.EmployeeJob;
import org.springframework.stereotype.Component;

@Component
public class WorkerMapper {
    public WorkerResponse toEmpResponse(EmployeeJob employeeJob){
        WorkerResponse workerResponse = new WorkerResponse();
        workerResponse.setEmpId(employeeJob.getAccount().getId());
        workerResponse.setEmpName(employeeJob.getAccount().getFullname());
        workerResponse.setJobEmpId(employeeJob.getJob().getId());
        workerResponse.setSrcPicture(employeeJob.getAccount().getProfilePicture());
        workerResponse.setDescription(employeeJob.getAccount().getBio());
        workerResponse.setCountRate(100);
        workerResponse.setRate(4.9);
        workerResponse.setLocation("Thu Duc, Ho Chi Minh");
        workerResponse.setJobName(employeeJob.getJob().getName());
        workerResponse.setPriceJob(employeeJob.getJob().getPrice());
        return workerResponse;
    }
}
