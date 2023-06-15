package com.portal.service;


import com.portal.entity.Category;
import com.portal.entity.Location;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.request.ApplyJobDto;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.JobNotFoundException;
import com.portal.response.JobResponse;

import java.util.List;
import java.util.Optional;

public interface ApplyJobService
{
    String applyJob(ApplyJobDto applyJobDto,long candidateId,long jobId,long companyId) throws CandidateDoesNotExistsException, JobNotFoundException, CompanyDoesNotExistsException;
    List<JobResponse> findJobsByCity(String location);
    List<JobResponse>findJobsBySalary(double salary);
    List<JobResponse>findJobByCategory(String category);
    List<JobResponse>findJobByCompany(String company);
    List<JobResponse>findJobsByTitle(String title);
    List<JobResponse> findByJobTitleOrSalaryOrLocationOrCompanyOrCategory(String jobTitle,double salary,String location,String company,String category);
    List<JobResponse> findJobs(List<Location> locationList,List<Category>categoryList);


}
