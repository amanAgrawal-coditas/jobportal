package com.portal.service;

import com.portal.request.CompanyDto;
import com.portal.request.PasswordDto;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.response.AppliedApplicationResponse;
import com.portal.response.CandidateResponse;
import com.portal.response.CompanyResponse;

import java.util.List;

public interface CompanyService
{
    List<CompanyResponse> getAllCompanies();
    String updateCompany(CompanyDto companyDto,long id) throws CompanyDoesNotExistsException;
    String deleteCompany(long id) throws CompanyDoesNotExistsException;
    String updatePassword(PasswordDto passwordDto,long id) throws CompanyDoesNotExistsException;
    CompanyResponse getCompanyById(long id);
    List<AppliedApplicationResponse>getCandidatesByJob(long jobId);
    String candidateAccepted(long jobId,long candidateId);
    String candidateRejected(long jobId,long candidateId);
}
