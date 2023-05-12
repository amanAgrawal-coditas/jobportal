package com.portal.service;

import com.portal.dto.CandidateDto;
import com.portal.dto.CompanyDto;
import com.portal.dto.LoginDto;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;

public interface AuthService
{
    String login(LoginDto loginDto);
    String companySignUp(CompanyDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException;
    String candidateSignUp(CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException;
}
