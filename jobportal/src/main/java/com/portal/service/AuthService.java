package com.portal.service;

import com.portal.dto.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.response.OtpDto;

public interface AuthService
{
    String login(LoginDto loginDto);
    String companySignUp(CompanyDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException;
    String candidateSignUp(CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException;
    boolean forgotPassword(ForgotPasswordDto forgotPasswordDto) throws CompanyDoesNotExistsException, CandidateDoesNotExistsException;
    OtpDto verifyEmail(OtpVerifyDto otpVerifyDto);
    Boolean changePasswordTrue(OtpVerifyDto otpVerifyDto);
    String changePasswordFalse();
}
