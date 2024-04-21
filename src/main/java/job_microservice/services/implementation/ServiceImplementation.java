package job_microservice.services.implementation;

import job_microservice.dto.JobAndCompanyDto;
import job_microservice.externals.CompanyModel;
import job_microservice.model.JobModel;
import job_microservice.repository.JobRepository;
import job_microservice.services.JobServices;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ServiceImplementation implements JobServices {
    /*private final List<Job> jobs = new ArrayList<>();*/ JobRepository jobRepository;

    public ServiceImplementation(JobRepository repository) {
        this.jobRepository = repository;
    }

    @Override
    public List<JobAndCompanyDto> findAll() {
        List<JobModel> jobModels = jobRepository.findAll();
        List<JobAndCompanyDto> jobAndCompanyDtos = new ArrayList<>();
        return jobModels.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private JobAndCompanyDto convertToDto(JobModel jobModel) {
        RestTemplate restTemplate = new RestTemplate();
        JobAndCompanyDto jobAndCompanyDto = new JobAndCompanyDto();
        jobAndCompanyDto.setJobModel(jobModel);
        CompanyModel companyModel = restTemplate.getForObject("http://127.0.0.1:8082/companies/" + jobModel.getCompanyId(), CompanyModel.class);
        jobAndCompanyDto.setCompanyModel(companyModel);
        return jobAndCompanyDto;
    }

    @Override
    public void createJob(JobModel job) {
        jobRepository.save(job);
    }

    @Override
    public JobModel getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, JobModel updatedJob) {
        Optional<JobModel> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            JobModel jobModel = jobOptional.get();
            jobModel.setTitle(updatedJob.getTitle());
            jobModel.setDescription(updatedJob.getDescription());
            jobModel.setMinSalary(updatedJob.getMinSalary());
            jobModel.setMaxSalary(updatedJob.getMaxSalary());
            jobModel.setLocation(updatedJob.getLocation());
            jobRepository.save(jobModel);
            return true;
        }
        return false;
    }
}
