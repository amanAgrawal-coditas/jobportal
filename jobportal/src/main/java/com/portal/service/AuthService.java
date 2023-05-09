package com.portal.service;

import com.portal.dto.CandidateSignupDto;
import com.portal.dto.CompanySignupDto;
import com.portal.dto.LoginDto;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;

public interface AuthService
{
    String login(LoginDto loginDto);
    String companySignUp(CompanySignupDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException;
    String candidateSignUp(CandidateSignupDto candidateSignupDto) throws CandidateAlreadyExistsException;
}
