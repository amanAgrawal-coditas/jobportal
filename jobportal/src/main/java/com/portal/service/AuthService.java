package com.portal.service;

import com.portal.dto.CandidateSignupDto;
import com.portal.dto.CompanySignupDto;
import com.portal.dto.LoginDto;
import com.portal.exception.CompanyAlreadyExistsException;

public interface AuthService
{
    String login(LoginDto loginDto);
    String CompanySignUp(CompanySignupDto companySignupDto) throws CompanyAlreadyExistsException;
    String candidateSignUp(CandidateSignupDto candidateSignupDto);
}
