package com.portal.service;

import com.portal.request.JobDto;
import com.portal.exception.CategoryNotFoundException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.exception.JobNotFoundException;
import com.portal.response.AppliedApplicationResponse;
import com.portal.response.JobResponse;

import java.util.List;

public interface JobService
{
 String addJob(JobDto jobDto, long companyId,long categoryId) throws CompanyDoesNotExistsException, CategoryNotFoundException;
 String updateJob(JobDto jobDto, long companyId,long categoryId,long jobId) throws CompanyDoesNotExistsException, CategoryNotFoundException, JobNotFoundException;
 String deleteJob(Long jobId,Long companyId) throws JobNotFoundException;
 List<JobResponse> getAllJobs();
 List<JobResponse> getAllJobsForCompany(Long companyId);
 List<AppliedApplicationResponse> getAppliedApplications(long jobId);
}
