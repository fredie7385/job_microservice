package job_microservice.controller;


import job_microservice.dto.JobAndCompanyDto;
import job_microservice.model.JobModel;
import job_microservice.services.JobServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobServices jobServices;

    public JobController(JobServices services) {
        this.jobServices = services;
    }

    @GetMapping
    public ResponseEntity<List<JobAndCompanyDto>> findAll() {
        return ResponseEntity.ok(jobServices.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody JobModel job) {
        jobServices.createJob(job);
        return new ResponseEntity<>("Job Created Successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobModel> getJobById(@PathVariable Long id) {
        JobModel jobModel = jobServices.getJobById(id);
        if (jobModel != null) return new ResponseEntity<>(jobModel, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean deleted = jobServices.deleteJobById(id);
        if (deleted) return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody JobModel updatedJob) {
        boolean updated = jobServices.updateJob(id, updatedJob);
        if (updated) return new ResponseEntity<>("Updated!", HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
