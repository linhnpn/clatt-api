package container.code.function.job.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Account;
import container.code.data.entity.Job;
import container.code.data.repository.JobRepository;
import container.code.function.account.service.filestorage.FileStorage;
import container.code.function.job.JobMapper;
import container.code.function.job.JobService;
import container.code.function.job.api.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobServiceImpl implements JobService {
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobMapper jobMapper;

    private Job findJob(Integer id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));
    }

    @Override
    public ResponseEntity<ResponseObject> getAllJob() {
        try {
            List<JobResponse> list = jobRepository.findAll().stream().map(jobMapper::toJobResponse).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getJob(Integer id) {
        try {
            Job job = findJob(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, jobMapper.toJobResponse(job)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> addJob(Job job, MultipartFile file) {
        try {
            job.setId(null);
            String url = fileStorage.uploadFile(file);
            job.setThumbnailJobImage(url);
            jobRepository.save(job);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Add new Job Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateJob(Job job, MultipartFile file) {
        try {
            Job existJob = findJob(job.getId());
            if (existJob != null) {
                if (file != null) {
                    String oldUrl = existJob.getThumbnailJobImage();
                    fileStorage.deleteFile(oldUrl);
                    String updateUrl = fileStorage.uploadFile(file);
                    existJob.setThumbnailJobImage(updateUrl);
                }
                if (job.getPrice() != null) {
                    existJob.setName(job.getName());
                    existJob.setPrice(job.getPrice());
                }
                if (job.getMeasureUnit() != null) {
                    existJob.setMeasureUnit(job.getMeasureUnit());
                }
                if (job.getName() != null) {
                    existJob.setName(job.getName());
                }
                jobRepository.save(existJob);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Updated Job Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteJob(Job job) {
        try {
            Job existJob = findJob(job.getId());
            String oldUrl = existJob.getThumbnailJobImage();
            fileStorage.deleteFile(oldUrl);
            jobRepository.delete(existJob);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Deleted Job Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }
}
