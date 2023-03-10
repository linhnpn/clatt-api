package container.code.function.job;


import container.code.data.entity.Job;
import container.code.function.job.api.JobResponse;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobResponse toJobResponse(Job job) {
        JobResponse jobResponse = new JobResponse();
        jobResponse.setId(job.getId());
        jobResponse.setJobName(job.getName());
        jobResponse.setPrice(job.getPrice());
        jobResponse.setMeasureUnit(job.getMeasureUnit());
        jobResponse.setThumbnailImage(job.getThumbnailJobImage());
        return jobResponse;
    }
}
