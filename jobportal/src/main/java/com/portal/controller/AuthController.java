package com.portal.controller;

import com.portal.entity.Candidate;
import com.portal.entity.Category;
import com.portal.entity.Location;
import com.portal.request.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.response.*;
import com.portal.security.JwtResponse;
import com.portal.security.JwtUtil;
import com.portal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private LocationService locationService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ApplyJobService jobService;

    @PostMapping("/candidateSignup")
    public ResponseEntity<CandidateSignupResponse> candidateSignup(@RequestBody CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException {

        CandidateSignupResponse response = authService.candidateSignUp(candidateSignupDto);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/companySignup")
    public ResponseEntity<CompanySignupResponse> companySignup(@RequestBody CompanyDto companySignupDto) throws CompanyAlreadyExistsException, CompanyDoesNotExistsException {
        CompanySignupResponse companySignupResponse = authService.companySignUp(companySignupDto);
        return new ResponseEntity<>(companySignupResponse, HttpStatus.CREATED);
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
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setImage(authService.getImage(userDetails.getUsername()));
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) throws CompanyDoesNotExistsException, CandidateDoesNotExistsException {
        boolean response = authService.forgotPassword(forgotPasswordDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verifyEmail")
    OtpDto verifyEmail(@RequestBody OtpVerifyDto otpVerifyDto) {

        return authService.verifyEmail(otpVerifyDto);
    }

    @PutMapping("/changePasswordTrue")
    public ResponseEntity<OtpDto> changePasswordTrue(@RequestBody PasswordVerifyDto passwordVerifyDto) {
        OtpDto otpDto = authService.changePasswordTrue(passwordVerifyDto);
        return new ResponseEntity<>(otpDto, HttpStatus.OK);
    }

    @GetMapping("/changePasswordFalse")
    public String changePasswordFalse() {
        return authService.changePasswordFalse();
    }

    @GetMapping("/getAllLocations")
    public List<LocationResponse> getAllLocations()
    {
        return locationService.getAllLocation();

    }
    @GetMapping("/getAllCategories")
    public List<CategoryResponse> getAllCategories()
    {
        return categoryService.getAllCategory();
    }
}