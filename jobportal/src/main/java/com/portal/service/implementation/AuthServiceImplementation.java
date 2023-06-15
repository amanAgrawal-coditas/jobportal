package com.portal.service.implementation;
import com.portal.request.*;
import com.portal.entity.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.repository.*;
import com.portal.response.CandidateSignupResponse;
import com.portal.response.CompanySignupResponse;
import com.portal.response.OtpDto;
import com.portal.service.AuthService;
import com.portal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import java.util.Objects;
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
    private CategoryRepository categoryRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public String login(LoginDto loginDto)
    {
        return "logged in successfully";
    }
    @Override
    public CompanySignupResponse companySignUp(CompanyDto companySignupDto) throws CompanyAlreadyExistsException
    {
        CompanySignupResponse companySignupResponse=new CompanySignupResponse();
        if (companyRepository.existsByEmail(companySignupDto.getEmail()))
        {
            throw new CompanyAlreadyExistsException(HttpStatus.BAD_REQUEST, "Email Already exists");
        }
        Company company = new Company();
        List<Long> locationList = companySignupDto.getLocationList();
        List<Location> locations= locationList.stream().filter(s->locationRepository.findById(s).isPresent()).map(id->locationRepository.findById(id).get()).collect(Collectors.toList());
        company.setLocations(locations);
        company.setDescription(companySignupDto.getDescription());
        company.setEmail(companySignupDto.getEmail());
        company.setPassword(passwordEncoder.encode(companySignupDto.getPassword()));
        company.setName(companySignupDto.getName());
        Roles roles = roleRepository.findByName("ROLE_COMPANY").get();
        company.setRole(roles);
        Company savedCompany = companyRepository.save(company);
        ProfileImage profileImage= new ProfileImage();
        profileImage.setImageData(companySignupDto.getImage());
        savedCompany.setProfileImage(profileImage);
        List<Long> categoryList = companySignupDto.getCategoryList();
        company.setCompanyCategoryList(categoryList.stream().map(id->{
            CompanyCategory companyCategory=new CompanyCategory();
            companyCategory.setCompany(savedCompany);
            companyCategory.setCategory(categoryRepository.findById(id).isPresent()?categoryRepository.findById(id).get():null);
            return companyCategory;
        }).collect(Collectors.toList()));
        companyRepository.save(savedCompany);
        emailService.sendEmail(companySignupDto.getEmail(), "Welcome to your company profile where you can hire right and required candidates for the purpose!", "Welcome aboard!");
        companySignupResponse.setMessage("Welcome to your profile");
        companySignupResponse.setHttpStatus(HttpStatus.OK);
        return companySignupResponse;
    }

    @Override
    public CandidateSignupResponse candidateSignUp(CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException
    {
        CandidateSignupResponse candidateResponse=new CandidateSignupResponse();
        if (candidateRepository.existsByEmail(candidateSignupDto.getEmail()))
        {
            throw new CandidateAlreadyExistsException(HttpStatus.BAD_REQUEST, "Candidate's email already exists");
        }
        Candidate candidate = new Candidate();
        candidate.setName(candidateSignupDto.getName());
        candidate.setEmail(candidateSignupDto.getEmail());
        candidate.setPhoneNumber(candidateSignupDto.getPhoneNumber());
        candidate.setPassword(passwordEncoder.encode(candidateSignupDto.getPassword()));
        Roles roles = roleRepository.findByName("ROLE_CANDIDATE").get();
        candidate.setRoles(roles);
        Candidate savedCandidate=candidateRepository.save(candidate);
        ProfileImage profileImage= new ProfileImage();
        profileImage.setImageData(candidateSignupDto.getImage());
        savedCandidate.setProfileImage(profileImage);
        candidateRepository.save(savedCandidate);
        candidateResponse.setHttpStatus(HttpStatus.OK);
        candidateResponse.setMessage("Welcome to your candidate profile");
        emailService.sendEmail(candidateSignupDto.getEmail(), "Welcome to your own job portal where you can apply jobs and get good placements", "You have been verified");
        return candidateResponse;
    }
    @Override
    public boolean forgotPassword(ForgotPasswordDto forgotPasswordDto) throws CompanyDoesNotExistsException, CandidateDoesNotExistsException
    {
        if ((forgotPasswordDto.getEmail()) != null && companyRepository.findByEmail(forgotPasswordDto.getEmail()).isPresent()) {
            Company company = companyRepository.findByEmail(forgotPasswordDto.getEmail()).get();
            long otp = Long.parseLong(new Random().ints(6, 0, 10).mapToObj(String::valueOf).collect(Collectors.joining()));
            company.setOtp(otp);
            emailService.sendEmail(company.getEmail(), "The Otp to reset your mail is " + otp, "Password reset mail");
            LocalTime currentTime = LocalTime.now();
            company.setCurrentTimeOtp(currentTime);
            company.setOtpActive(true);
            Company updatedCompany = companyRepository.save(company);
            return true;
        } else if ((forgotPasswordDto.getEmail()) != null && candidateRepository.findByEmail(forgotPasswordDto.getEmail()).isPresent()) {
            Candidate candidate = candidateRepository.findByEmail(forgotPasswordDto.getEmail()).get();
            long otp = Long.parseLong(new Random().ints(6, 0, 10).mapToObj(String::valueOf).collect(Collectors.joining()));
            candidate.setOtp(otp);
            LocalTime currentTime = LocalTime.now();
            candidate.setCurrentTimeOtp(currentTime);
            candidate.setOtpActive(true);
            Candidate updatedCandidate = candidateRepository.save(candidate);
            emailService.sendEmail(candidate.getEmail(), "The Otp to reset your mail is " + otp, "Password reset mail");
            return true;
        }

        return false;
    }
    @Override
    public OtpDto verifyEmail(OtpVerifyDto otpVerifyDto)
    {
        OtpDto otpDto = new OtpDto();
        if (otpVerifyDto.getEmail() != null && companyRepository.findByEmail(otpVerifyDto.getEmail()).isPresent()) {

            Company company = companyRepository.findByEmail(otpVerifyDto.getEmail()).get();
            LocalTime receivedTime = company.getCurrentTimeOtp();
            LocalTime currentTime = LocalTime.now();
            if (currentTime.minusSeconds(50).isAfter(receivedTime)) {
                company.setOtpActive(false);
            } else {
                company.setOtpActive(true);
            }
            Company updatedCompany = companyRepository.save(company);
            if (otpVerifyDto.getOtp() == company.getOtp())
            {
                if (updatedCompany.isOtpActive() == true)
                {
                    otpDto.setMessage("Otp successfully matched");
                    otpDto.setStatus(true);
                } else {
                    otpDto.setMessage("Otp expired");
                    otpDto.setStatus(false);
                }
            }
            else
            {
                otpDto.setMessage("Incorrect Otp");
                otpDto.setStatus(false);
            }
            return otpDto;
        } else if (otpVerifyDto.getEmail() != null && candidateRepository.findByEmail(otpVerifyDto.getEmail()).isPresent())
        {
            Candidate candidate = candidateRepository.findByEmail(otpVerifyDto.getEmail()).get();
            LocalTime receivedTime = candidate.getCurrentTimeOtp();
            LocalTime currentTime = LocalTime.now();
            if (currentTime.minusSeconds(50).isAfter(receivedTime))
            {
                candidate.setOtpActive(false);
            } else
            {
                candidate.setOtpActive(true);
            }
            Candidate updatedCandidate = candidateRepository.save(candidate);
            if (otpVerifyDto.getOtp() == candidate.getOtp())
            {
                if (updatedCandidate.isOtpActive() == true)
                {
                    otpDto.setMessage("Otp successfully matched");
                    otpDto.setStatus(true);
                } else
                {
                    otpDto.setMessage("Otp expired");
                    otpDto.setStatus(false);
                }
            }
            else
            {
                otpDto.setMessage("Incorrect Otp");
                otpDto.setStatus(false);
            }
            return otpDto;
        } else
        {
            otpDto.setMessage("Something went wrong please try again");
            otpDto.setStatus(false);
            return otpDto;
        }
    }

    @Override
    public OtpDto changePasswordTrue(PasswordVerifyDto passwordVerifyDto)
    {
        OtpDto otpDto=new OtpDto();
        if (passwordVerifyDto.getEmail() != null && companyRepository.findByEmail(passwordVerifyDto.getEmail()).isPresent()) {
            Company company = companyRepository.findByEmail(passwordVerifyDto.getEmail()).get();
            if (Objects.equals(passwordVerifyDto.getNewPassword(), passwordVerifyDto.getReTypePassword()))
            {
                company.setPassword(passwordEncoder.encode(passwordVerifyDto.getNewPassword()));
                Company updatedCompany = companyRepository.save(company);
                emailService.sendEmail(passwordVerifyDto.getEmail(), "Your password has been updated if it was not you please let us know ", "Changed successfully");
                otpDto.setMessage("Password changed");
                otpDto.setStatus(true);
            }
            else
            {
                otpDto.setMessage("passwords do not match");
                otpDto.setStatus(false);
            }
        }
        else if (passwordVerifyDto.getEmail() != null && candidateRepository.findByEmail(passwordVerifyDto.getEmail()).isPresent())
        {
            Candidate candidate = candidateRepository.findByEmail(passwordVerifyDto.getEmail()).get();
            if (Objects.equals(passwordVerifyDto.getNewPassword(), passwordVerifyDto.getReTypePassword()))
            {
                candidate.setPassword(passwordEncoder.encode(passwordVerifyDto.getNewPassword()));
                Candidate updatedCandidate = candidateRepository.save(candidate);
                emailService.sendEmail(passwordVerifyDto.getEmail(), "Your password has been updated if it was not you please let us know ", "Changed successfully");
                otpDto.setMessage("Password changed");
                otpDto.setStatus(true);
            }
            else
            {
                otpDto.setMessage("passwords do not match");
                otpDto.setStatus(false);
            }
        }
        else
        {
            otpDto.setMessage("Error occurred try again!");
            otpDto.setStatus(false);
        }
        System.out.println(otpDto.isStatus());
        return otpDto;
    }

    @Override
    public String changePasswordFalse()
    {
        return "Otp does not match";
    }
    @Override
    public byte[] getImage(String username)
    {
        Optional<Candidate> candidateOptional = candidateRepository.findByEmail(username);
        if (candidateOptional.isPresent())
        {
            return candidateOptional.get().getProfileImage().getImageData();
        }
        else
        {
            Optional<Company> companyOptional = companyRepository.findByEmail(username);
            return companyOptional.map(company -> company.getProfileImage().getImageData()).orElse(null);
        }
    }
}