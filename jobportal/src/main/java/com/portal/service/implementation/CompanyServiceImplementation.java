package com.portal.service.implementation;

import com.portal.dto.CompanyDto;
import com.portal.dto.PasswordDto;
import com.portal.entity.Company;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.repository.CompanyRepository;
import com.portal.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyServiceImplementation implements CompanyService
{
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Company> getAllCompanies()
    {
        return companyRepository.findAll();
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
        if (company.getLocations()!=null &&company.getJobsList()!=companyDto.getJobsList())
        {
            company.setJobsList(companyDto.getJobsList());
        }
        if (company.getProfileImage()!=null&&company.getProfileImage()!=companyDto.getProfileImage())
        {
            company.setProfileImage(companyDto.getProfileImage());
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
}
