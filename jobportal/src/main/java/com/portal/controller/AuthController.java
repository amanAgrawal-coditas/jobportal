package com.portal.controller;

import com.portal.dto.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.response.OtpDto;
import com.portal.security.JwtResponse;
import com.portal.security.JwtUtil;
import com.portal.service.AuthService;
import com.portal.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/candidateSignup")
    public ResponseEntity<String> candidateSignup(@RequestBody CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException {
        String response = authService.candidateSignUp(candidateSignupDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/companySignup")
    public ResponseEntity<String> companySignup(@RequestBody CompanyDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException {
        String response = authService.companySignUp(companySignupDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody LoginDto loginDto) {
        UserDetails userDetails = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("Invalid Credentials");
        }
        userDetails = customUserDetailService.loadUserByUsername(loginDto.getEmail());
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwtUtil.generateToken(userDetails));
        jwtResponse.setRole(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0));
        return ResponseEntity.ok(jwtResponse);
    }
        @PostMapping("/forgotPassword")
        public ResponseEntity<Boolean> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) throws CompanyDoesNotExistsException, CandidateDoesNotExistsException
        {
            boolean response = authService.forgotPassword(forgotPasswordDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        @PostMapping("/verifyEmail")
        OtpDto verifyEmail (@RequestBody OtpVerifyDto otpVerifyDto)
        {

            return authService.verifyEmail(otpVerifyDto);
        }
        @PutMapping("/changePasswordTrue")
        public ResponseEntity<Boolean> changePasswordTrue (@RequestBody OtpVerifyDto otpVerifyDto)
        {
            Boolean response = authService.changePasswordTrue(otpVerifyDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        @GetMapping("/changePasswordFalse")
        public String changePasswordFalse ()
        {
            return authService.changePasswordFalse();
        }

    }