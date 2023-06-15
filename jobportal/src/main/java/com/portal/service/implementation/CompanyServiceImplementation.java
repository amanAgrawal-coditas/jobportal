package com.portal.service.implementation;

import com.portal.entity.*;
import com.portal.repository.AppliedApplicationRepository;
import com.portal.repository.CandidateRepository;
import com.portal.repository.JobRepository;
import com.portal.request.CompanyDto;
import com.portal.request.PasswordDto;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.repository.CompanyRepository;
import com.portal.response.AppliedApplicationResponse;
import com.portal.response.CandidateResponse;
import com.portal.response.CompanyResponse;
import com.portal.service.CompanyService;
import com.portal.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImplementation implements CompanyService
{
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AppliedApplicationRepository appliedApplicationRepository;

    @Override
    public List<CompanyResponse> getAllCompanies()
    {
        return companyRepository.findAll().stream().map((element) -> modelMapper.map(element, CompanyResponse.class)).collect(Collectors.toList());
    }

    @Override
    public String updateCompany(CompanyDto companyDto, long id) throws CompanyDoesNotExistsException {
        Company company=companyRepository.findById(id).orElseThrow(()->new CompanyDoesNotExistsException(HttpStatus.BAD_REQUEST,"The company does not exists"));
        if (company.getDescription()!=null&& company.getDescription()!=companyDto.getDescription())
        {
            company.setDescription(companyDto.getDescription());
        }
        if (company.getName()!=null&&company.getName()!=companyDto.getName())
        {
            company.setName(companyDto.getName());
        }
        if (company.getEmail()!=null&&company.getEmail()!=companyDto.getEmail())
        {
            company.setEmail(companyDto.getEmail());
        }
        if (company.getProfileImage()!=null&& !Objects.equals(company.getProfileImage(),companyDto.getImage()))
        {
            ProfileImage image=modelMapper.map(company.getProfileImage(), ProfileImage.class);
            company.setProfileImage(image);
        }
        Company updatedCompany=companyRepository.save(company);
        return "Details have been updated";
    }
    @Override
    public String deleteCompany(long id) throws CompanyDoesNotExistsException
    {
        Company company=companyRepository.findById(id).orElseThrow(()->new CompanyDoesNotExistsException(HttpStatus.BAD_REQUEST,"Company does not exists"));
        companyRepository.delete(company);
        return "Company has been deleted";
    }
    @Override
    public String updatePassword(PasswordDto passwordDto, long id) throws CompanyDoesNotExistsException {
        Company company=companyRepository.findById(id).orElseThrow(()->new CompanyDoesNotExistsException(HttpStatus.BAD_REQUEST,"Company does not exists"));
        boolean check= passwordEncoder.matches(passwordDto.getOldPassword(),company.getPassword())&&!Objects.equals(passwordDto.getOldPassword(), passwordDto.getNewPassword());
        {
            if (check)
            {
                company.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
                Company updatedPassword=companyRepository.save(company);
                return "Password has been updated";
            }
            else if (Objects.equals(passwordDto.getOldPassword(), passwordDto.getNewPassword()))
            {
                return "Old password is matching the new one";
            }
            else
            {
            return "Entered password is incorrect";
            }
        }
    }

    @Override
    public CompanyResponse getCompanyById(long id)
    {
        Company company= companyRepository.findById(id).get();
        return modelMapper.map(company, CompanyResponse.class);
    }
    @Override
    public List<AppliedApplicationResponse>getCandidatesByJob(long jobId)
    {
        Jobs jobs=jobRepository.findById(jobId).get();
        List<AppliedApplication> appliedApplication=jobs.getApplicationList();
        return appliedApplication.stream().map((element) -> modelMapper.map(element, AppliedApplicationResponse.class)).collect(Collectors.toList());
    }

    @Override
    public String candidateAccepted(long jobId,long candidateId)
    {
        Jobs jobs=jobRepository.findById(jobId).get();
        Candidate candidate=candidateRepository.findById(candidateId).get();
        AppliedApplication appliedApplication=candidate.getApplicationList().stream().filter(appliedApplicationList -> appliedApplicationList.getJobs().getJobId() == jobId).collect(Collectors.toList()).get(0);
        appliedApplication.setStatus(JobStatus.STATUS_ACCEPTED.toString());
        appliedApplicationRepository.save(appliedApplication);
        emailService.sendEmail(candidate.getEmail(),"Accepted","Your interview has been scheduled. Hr will connect you with the same");
        return "Accepted";
    }

    @Override
    public String candidateRejected(long jobId,long candidateId)
    {
        Jobs jobs=jobRepository.findById(jobId).get();
        Candidate candidate=candidateRepository.findById(candidateId).get();
        AppliedApplication appliedApplication=candidate.getApplicationList().stream().filter(appliedApplicationList -> appliedApplicationList.getJobs().getJobId() == jobId).collect(Collectors.toList()).get(0);
        appliedApplication.setStatus(JobStatus.STATUS_REJECTED.toString());
        appliedApplicationRepository.save(appliedApplication);
        emailService.sendEmail(candidate.getEmail(),"Rejected","Your application has not been moved forward");
        return "Rejected";
    }
}
