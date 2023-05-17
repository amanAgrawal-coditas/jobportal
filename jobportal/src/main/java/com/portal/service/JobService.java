package com.portal.service;

import com.portal.dto.JobDto;
import com.portal.exception.CategoryNotFoundException;
import com.portal.exception.CompanyDoesNotExistsException;

public interface JobService
{
 String addJob(JobDto jobDto, long companyId,long categoryId) throws CompanyDoesNotExistsException, CategoryNotFoundException;
}
