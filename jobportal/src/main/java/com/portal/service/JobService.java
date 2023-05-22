package com.portal.service;

import com.portal.dto.JobDto;
import com.portal.exception.CategoryNotFoundException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.exception.JobNotFoundException;

public interface JobService
{
 String addJob(JobDto jobDto, long companyId,long categoryId) throws CompanyDoesNotExistsException, CategoryNotFoundException;
 String updateJob(JobDto jobDto, long companyId,long categoryId,long jobId) throws CompanyDoesNotExistsException, CategoryNotFoundException, JobNotFoundException;
 String deleteJob(long jobId,long companyId) throws JobNotFoundException;
}
