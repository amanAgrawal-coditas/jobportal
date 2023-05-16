package com.portal.service.implementation;

import com.portal.dto.*;
import com.portal.entity.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.repository.*;
import com.portal.service.AuthService;
import com.portal.service.EmailService;
import com.portal.service.EmailService;
import com.portal.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class AuthServiceImplementation implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ProfileImageRepository profileImageRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public String login(LoginDto loginDto) {
        return "logged in successfully";
    }

    @Override
    public String companySignUp(CompanyDto companySignupDto) throws CompanyAlreadyExistsException {
        if (companyRepository.existsByEmail(companySignupDto.getEmail())) {
            throw new CompanyAlreadyExistsException(HttpStatus.BAD_REQUEST, "Email Already exists");
        }
        Company company = new Company();
        List<String> categoryList = companySignupDto.getCategoryList();
        List<Category> categoryList1 = categoryList.stream().map(s1 -> {
            Optional<Category> categoryOptional = categoryRepository.findByCategoryName(s1);
            if (categoryOptional.isPresent()) {
                return categoryOptional.get();
            } else {
                Category category = new Category();
                category.setCategoryName(s1);
                return categoryRepository.save(category);
            }
        }).collect(Collectors.toList());

        List<String> locationList = companySignupDto.getLocationList();
        List<Location> locations = locationList.stream().map(l1 -> {
            Optional<Location> optionalLocation = locationRepository.findByLocationName(l1);
            if (optionalLocation.isPresent()) {
                return optionalLocation.get();
            } else {
                Location location = new Location();
                location.setLocationName(l1);
                return locationRepository.save(location);
            }
        }).collect(Collectors.toList());
        company.setDescription(companySignupDto.getDescription());
        company.setEmail(companySignupDto.getEmail());
        company.setPassword(passwordEncoder.encode(companySignupDto.getPassword()));
        company.setName(companySignupDto.getName());
        Roles roles = roleRepository.findByName("ROLE_COMPANY").get();
        company.setRole(roles);
        long otp = Long.parseLong(new Random().ints(6, 0, 10).mapToObj(String::valueOf).collect(Collectors.joining()));
        ProfileImage profileImage = companySignupDto.getProfileImage();
        profileImage.setImageData(ImageUtil.compressImage(profileImage.getImageData()));
        company.setProfileImage(profileImage);
        company.setOtp(otp);
        Company savedCompany = companyRepository.save(company);
        List<CompanyCategory> companyCategoryList = categoryList1.stream().map(category -> {
            CompanyCategory companyCategory = new CompanyCategory();
            companyCategory.setCompany(savedCompany);
            companyCategory.setCategory(category);
            return companyCategory;
        }).collect(Collectors.toList());
        List<Location> locationList1 = locations.stream().map(loc -> {
            Location location = new Location();
            location.setCompany(savedCompany);
            location.setLocationName(loc.getLocationName());
            return location;
        }).collect(Collectors.toList());
        company.setLocations(locationList1);
        company.setCategoryList(companyCategoryList);
        companyRepository.save(savedCompany);
        return "Welcome to your company profile";
    }

    @Override
    public String candidateSignUp(CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException {
        if (candidateRepository.existsByEmail(candidateSignupDto.getEmail())) {
            throw new CandidateAlreadyExistsException(HttpStatus.BAD_REQUEST, "Candidate's email already exists");
        }
        Candidate candidate = new Candidate();
        candidate.setName(candidateSignupDto.getName());
        candidate.setEmail(candidateSignupDto.getEmail());
        candidate.setPhoneNumber(candidateSignupDto.getPhoneNumber());
        candidate.setPassword(passwordEncoder.encode(candidateSignupDto.getPassword()));
        Roles roles = roleRepository.findByName("ROLE_CANDIDATE").get();
        System.out.println(roles.getName());
        candidate.setRoles(roles);
        long otp = Long.parseLong(new Random().ints(6, 0, 10).mapToObj(String::valueOf).collect(Collectors.joining()));
        candidate.setOtp(otp);
        ProfileImage profileImage = candidateSignupDto.getImage();
        profileImage.setImageData(ImageUtil.compressImage(profileImage.getImageData()));
        candidate.setProfileImage(profileImage);
        candidateRepository.save(candidate);
        return "Welcome to your candidate profile";
    }

    @Override
//    @EventListener(ApplicationReadyEvent.class)
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) throws CompanyDoesNotExistsException, CandidateDoesNotExistsException {
        if ((forgotPasswordDto.getEmail()) != null && companyRepository.findByEmail(forgotPasswordDto.getEmail()).isPresent()) {
            Company company = companyRepository.findByEmail(forgotPasswordDto.getEmail()).get();
            emailService.sendEmail(company.getEmail(), "The Otp to reset your mail is" + company.getOtp(), "Password reset mail");
            return "Otp sent to your mail";
        } else if ((forgotPasswordDto.getEmail()) != null && candidateRepository.findByEmail(forgotPasswordDto.getEmail()).isPresent()) {
            Candidate candidate = candidateRepository.findByEmail(forgotPasswordDto.getEmail()).get();
            emailService.sendEmail(candidate.getEmail(), "The Otp to reset your mail is" + candidate.getOtp(), "Password reset mail");
            return "Otp sent your mail";
        } else {
            return "Email does not exists";
        }

    }

    @Override
    public Boolean verifyEmail(OtpVerifyDto otpVerifyDto) {
        if (otpVerifyDto.getEmail() != null && companyRepository.findByEmail(otpVerifyDto.getEmail()).isPresent()) {
            Company company = companyRepository.findByEmail(otpVerifyDto.getEmail()).get();
            if (otpVerifyDto.getOtp() == company.getOtp()) {
                return true;
            } else {
                return false;
            }
        } else if (otpVerifyDto.getEmail() != null && candidateRepository.findByEmail(otpVerifyDto.getEmail()).isPresent()) {
            Candidate candidate = candidateRepository.findByEmail(otpVerifyDto.getEmail()).get();
            if (otpVerifyDto.getOtp() == candidate.getOtp()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String changePasswordTrue(OtpVerifyDto otpVerifyDto)
    {
        if (otpVerifyDto.getEmail() != null && companyRepository.findByEmail(otpVerifyDto.getEmail()).isPresent())
        {
            Company company = companyRepository.findByEmail(otpVerifyDto.getEmail()).get();
            company.setPassword(passwordEncoder.encode(otpVerifyDto.getPassword()));
            return "your password has been changed";
        }
        else if (otpVerifyDto.getEmail() != null && candidateRepository.findByEmail(otpVerifyDto.getEmail()).isPresent())
        {
            Candidate candidate = candidateRepository.findByEmail(otpVerifyDto.getEmail()).get();
            candidate.setPassword(passwordEncoder.encode(otpVerifyDto.getPassword()));
            return "your password has been changed";
        }
        else
            return "check your email";
    }
    @Override
    public String changePasswordFalse()
    {
        return "Otp does not match";
    }
}