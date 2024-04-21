package job_microservice.dto;


import job_microservice.externals.CompanyModel;
import job_microservice.model.JobModel;

public class JobAndCompanyDto {
    private JobModel jobModel;
    private CompanyModel companyModel;

    public JobModel getJobModel() {
        return jobModel;
    }

    public void setJobModel(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }
}
