package com.portal.controller;

import com.portal.dto.CandidateSignupDto;
import com.portal.dto.CompanySignupDto;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private AuthService authService;
    @PostMapping("/candidateSignup")
    public ResponseEntity<String>candidateSignup(@RequestBody CandidateSignupDto candidateSignupDto) throws CandidateAlreadyExistsException {
        String response=authService.candidateSignUp(candidateSignupDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/companySignup")
    public ResponseEntity<String>companySignup(@RequestBody CompanySignupDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException {
        String response=authService.companySignUp(companySignupDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
