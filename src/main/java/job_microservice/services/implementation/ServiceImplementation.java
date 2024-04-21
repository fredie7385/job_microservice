package job_microservice.services.implementation;

import job_microservice.dto.JobAndCompanyDto;
import job_microservice.externals.CompanyModel;
import job_microservice.model.JobModel;
import job_microservice.repository.JobRepository;
import job_microservice.services.JobServices;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // This class is likely used to make HTTP requests

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ServiceImplementation implements JobServices {
    /*private final List<Job> jobs = new ArrayList<>();*/
    private final JobRepository jobRepository; // Injected dependency for the job repository

    public ServiceImplementation(JobRepository repository) {
        this.jobRepository = repository;
    }

    @Override
    public List<JobAndCompanyDto> findAll() {
        // Find all jobs from the repository
        List<JobModel> jobModels = jobRepository.findAll();
        // Create a new list to hold JobAndCompanyDto objects
        List<JobAndCompanyDto> jobAndCompanyDtos = new ArrayList<>();
        // Convert each JobModel to a JobAndCompanyDto and add it to the list
        return jobModels.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private JobAndCompanyDto convertToDto(JobModel jobModel) {
        // Create a new RestTemplate to make an HTTP request
        RestTemplate restTemplate = new RestTemplate();
        // Create a new JobAndCompanyDto object
        JobAndCompanyDto jobAndCompanyDto = new JobAndCompanyDto();
        // Set the JobModel property of the JobAndCompanyDto
        jobAndCompanyDto.setJobModel(jobModel);
        // Use the RestTemplate to get the company information from an external API
               CompanyModel companyModel = restTemplate.getForObject("http://127.0.0.1:8082/companies/" + jobModel.getCompanyId(), CompanyModel.class);
        // Set the CompanyModel property of the JobAndCompanyDto
        jobAndCompanyDto.setCompanyModel(companyModel);
        return jobAndCompanyDto;
    }

    @Override
    public void createJob(JobModel job) {
        // Save the job to the repository
        jobRepository.save(job);
    }

    @Override
    public JobModel getJobById(Long id) {
        // Find the job by ID from the repository
        // Return the job if it exists, otherwise return null
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
