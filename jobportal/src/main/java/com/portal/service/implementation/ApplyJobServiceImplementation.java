package com.portal.service.implementation;

import com.portal.entity.*;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.repository.CompanyRepository;
import com.portal.request.ApplyJobDto;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.JobNotFoundException;
import com.portal.repository.AppliedApplicationRepository;
import com.portal.repository.CandidateRepository;
import com.portal.repository.JobRepository;
import com.portal.response.JobResponse;
import com.portal.service.ApplyJobService;
import com.portal.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplyJobServiceImplementation implements ApplyJobService
{
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AppliedApplicationRepository appliedApplicationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public String applyJob(ApplyJobDto applyJobDto, long candidateId, long jobId,long companyId) throws CandidateDoesNotExistsException, JobNotFoundException, CompanyDoesNotExistsException {
        Candidate candidate=candidateRepository.findById(candidateId).orElseThrow(()->new CandidateDoesNotExistsException(HttpStatus.BAD_REQUEST,"The candidate does not exists"));
        Jobs jobs=jobRepository.findById(jobId).orElseThrow(()->new JobNotFoundException(HttpStatus.BAD_REQUEST,"Job does not exists"));
        Company company=companyRepository.findById(companyId).orElseThrow(()->new CompanyDoesNotExistsException(HttpStatus.BAD_REQUEST,"Company does not exists"));
        AppliedApplication appliedApplication=new AppliedApplication();
        appliedApplication.setCandidate(candidate);
        appliedApplication.setDescription(applyJobDto.getDescription());
        appliedApplication.setEmail(appliedApplication.getEmail());
        appliedApplication.setExpectedSalary(applyJobDto.getExpectedSalary());
        appliedApplication.setJobs(jobs);
        appliedApplication.setName(applyJobDto.getName());
        appliedApplication.setStatus(JobStatus.STATUS_PENDING.toString());
        appliedApplicationRepository.save(appliedApplication);
        emailService.sendEmail(appliedApplication.getEmail(),"Your application has been applied...","Status_pending");
        return "The job has been applied";
    }

    @Override
    public  List<JobResponse> findJobsByCity(String location)
    {
        List<JobResponse> jobResponseList =new ArrayList<>();
        Optional<Jobs> jobs=jobRepository.findByLocation(location);
        if (jobs.isPresent())
        {
            jobResponseList= Collections.singletonList(modelMapper.map(jobs, JobResponse.class));
        }
        return jobResponseList;
    }

    @Override
    public List<JobResponse> findJobsBySalary(double salary)
    {
        List<JobResponse> jobResponseList =new ArrayList<>();
        Optional<Jobs>jobs=jobRepository.findBySalary(salary);
        if (jobs.isPresent())
        {
            jobResponseList=Collections.singletonList(modelMapper.map(jobs,JobResponse.class));
        }
        return jobResponseList;
    }

    @Override
    public List<JobResponse> findJobByCategory(String category)
    {
        List<JobResponse> jobResponseList =new ArrayList<>();
        Optional<Jobs> job=jobRepository.findByCategory(category);
        if (job.isPresent())
        {
            jobResponseList=Collections.singletonList(modelMapper.map(job, JobResponse.class));
        }
        return jobResponseList;
    }

    @Override
    public List<JobResponse> findJobByCompany(String company)
    {
        List<JobResponse> jobResponseList =new ArrayList<>();
        Optional<Jobs> job=jobRepository.findByCompany(company);
        if (job.isPresent())
        {
            jobResponseList=Collections.singletonList(modelMapper.map(job, JobResponse.class));
        }
        return jobResponseList;
    }

    @Override
    public List<JobResponse> findJobsByTitle(String title) {
        List<JobResponse> jobResponseList =new ArrayList<>();
        Optional<Jobs> jobs=jobRepository.findByJobTitle(title);
        if (jobs.isPresent())
        {
            jobResponseList=Collections.singletonList(modelMapper.map(jobs, JobResponse.class));
        }
        return jobResponseList;
    }

    @Override
    public List<JobResponse> findByJobTitleOrSalaryOrLocationOrCompanyOrCategory(String jobTitle, double salary, String location, String company, String category) {
        List<JobResponse> jobResponseList =new ArrayList<>();
        Optional<Jobs> jobs=jobRepository.findByJobTitleOrSalaryOrLocationOrCompanyOrCategory(jobTitle,salary,location,company,category);
        if (jobs.isPresent())
        {
            jobResponseList=Collections.singletonList(modelMapper.map(jobs, JobResponse.class));
        }
        return jobResponseList;
    }

    @Override
    public List<JobResponse> findJobs(List<Location> locationList,List<Category> categoryList) {
      List<Jobs> jobList=  jobRepository.findAll().stream().filter(job->job.getLocation().containsAll(locationList)&&job.getCategory().getCategoryList().containsAll(categoryList)).collect(Collectors.toList());
        System.out.println(jobList);

        return null;
    }
}
