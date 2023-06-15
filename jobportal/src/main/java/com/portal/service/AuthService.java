package com.portal.service;

import com.portal.request.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.response.CandidateSignupResponse;
import com.portal.response.CompanySignupResponse;
import com.portal.response.OtpDto;

public interface AuthService
{
    String login(LoginDto loginDto);
    CompanySignupResponse companySignUp(CompanyDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException;
    CandidateSignupResponse candidateSignUp(CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException;
    boolean forgotPassword(ForgotPasswordDto forgotPasswordDto) throws CompanyDoesNotExistsException, CandidateDoesNotExistsException;
    OtpDto verifyEmail(OtpVerifyDto otpVerifyDto);
    OtpDto changePasswordTrue(PasswordVerifyDto passwordVerifyDto);
    String changePasswordFalse();

    byte[] getImage(String username);
}
