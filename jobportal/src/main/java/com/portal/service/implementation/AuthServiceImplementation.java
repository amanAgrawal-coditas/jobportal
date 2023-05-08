package com.portal.service.implementation;

import com.portal.dto.CandidateSignupDto;
import com.portal.dto.CompanySignupDto;
import com.portal.dto.LoginDto;
import com.portal.entity.*;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.repository.CandidateRepository;
import com.portal.repository.CompanyRepository;
import com.portal.repository.RoleRepository;
import com.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImplementation implements AuthService
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProfileImage profileImage;


    @Override
    public String login(LoginDto loginDto) {
        return "logged in successfully";
    }

    @Override
    public String CompanySignUp(CompanySignupDto companySignupDto) throws CompanyAlreadyExistsException {
        if (companyRepository.existsByEmail(companySignupDto.getEmail()))
        {
            throw new CompanyAlreadyExistsException(HttpStatus.BAD_GATEWAY,"Email Already exists");
        }

            Company company=new Company();
            company.setDescription(companySignupDto.getDescription());
            company.setEmail(companySignupDto.getEmail());
            company.setPassword(companySignupDto.getPassword());
            company.setName(companySignupDto.getName());
            List<Location> locations = new ArrayList<>();
            companySignupDto.getLocationList().stream().forEach(location -> locations.add(location));
            company.setLocations(locations);
            Roles roles=roleRepository.findByName("ROLE_COMPANY").get();
            company.setRole(roles);
            List<CompanyCategory> companyCategoryList=new ArrayList<>();
            companySignupDto.getCompanyCategoryList().stream().forEach(companyCategory -> companyCategoryList.add(companyCategory));
            companyRepository.save(company);
        return "Welcome to your company profile";
    }

    @Override
    public String candidateSignUp(CandidateSignupDto candidateSignupDto) {
        return "Welcome to your candidate profile";
    }
}
