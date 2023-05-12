package com.portal.service.implementation;

import com.portal.dto.CandidateDto;
import com.portal.dto.CompanyDto;
import com.portal.dto.LoginDto;
import com.portal.entity.*;
import com.portal.exception.CandidateAlreadyExistsException;
import com.portal.exception.CompanyAlreadyExistsException;
import com.portal.repository.*;
import com.portal.service.AuthService;
import com.portal.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    private LocationRepository locationRepository;
    @Autowired
    private AppliedApplicationRepository appliedApplicationRepository;
    @Autowired
    private ProfileImageRepository profileImageRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public String login(LoginDto loginDto) {
        return "logged in successfully";
    }

    @Override
    public String companySignUp(CompanyDto companySignupDto) throws CompanyAlreadyExistsException
    {
        if (companyRepository.existsByEmail(companySignupDto.getEmail()))
        {
            throw new CompanyAlreadyExistsException(HttpStatus.BAD_REQUEST,"Email Already exists");
        }
            Company company=new Company();
            company.setDescription(companySignupDto.getDescription());
            company.setEmail(companySignupDto.getEmail());
            company.setPassword(passwordEncoder.encode(companySignupDto.getPassword()));
            company.setName(companySignupDto.getName());
            List<Location>locationList=companySignupDto.getLocationList();
            company.setLocations(locationList);
//            List<Location> locations = companySignupDto.getLocationList()
//                    .stream()
//                    .map(id -> locationRepository.findById(id).get())
//                    .collect(Collectors.toList());
//                company.setLocations(locations);
                Roles roles=roleRepository.findByName("ROLE_COMPANY").get();
                company.setRole(roles);
                List<Category>categoryList=companySignupDto.getCategoryList();
                company.setCategoryList(categoryList);
//                List<CompanyCategory> companyCategoryList=companySignupDto.getCompanyCategoryList()
//                        .stream()
//                                .map(companyCategoryId->companyCategoryRepository.findById(companyCategoryId).get())
//                                         .collect(Collectors.toList());
                ProfileImage profileImage=companySignupDto.getProfileImage();
                profileImage.setImageData(ImageUtil.compressImage(profileImage.getImageData()));
                company.setProfileImage(profileImage);
                companyRepository.save(company);
                return "Welcome to your company profile";
    }

    @Override
    public String candidateSignUp(CandidateDto candidateSignupDto) throws CandidateAlreadyExistsException {
        if (candidateRepository.existsByEmail(candidateSignupDto.getEmail()))
        {
            throw new CandidateAlreadyExistsException(HttpStatus.BAD_REQUEST,"Candidate's email already exists");
        }
        Candidate candidate=new Candidate();
        candidate.setName(candidateSignupDto.getName());
        candidate.setEmail(candidateSignupDto.getEmail());
        candidate.setPhoneNumber(candidateSignupDto.getPhoneNumber());
        candidate.setPassword(passwordEncoder.encode(candidateSignupDto.getPassword()));
        Roles roles=roleRepository.findByName("ROLE_CANDIDATE").get();
        System.out.println(roles.getName());
        candidate.setRoles(roles);
        ProfileImage profileImage=candidateSignupDto.getImage();
        profileImage.setImageData(ImageUtil.compressImage(profileImage.getImageData()));
        candidate.setProfileImage(profileImage);
          candidateRepository.save(candidate);
        return "Welcome to your candidate profile";
    }
}
