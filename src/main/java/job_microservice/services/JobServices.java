package job_microservice.services;


import job_microservice.dto.JobAndCompanyDto;
import job_microservice.model.JobModel;

import java.util.List;

public interface JobServices {

    List<JobAndCompanyDto> findAll();

    void createJob(JobModel job);

    JobModel getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, JobModel updatedJob);
}