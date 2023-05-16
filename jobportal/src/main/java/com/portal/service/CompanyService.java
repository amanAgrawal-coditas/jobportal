package com.portal.service;

import com.portal.dto.CompanyDto;
import com.portal.dto.PasswordDto;
import com.portal.entity.Company;
import com.portal.exception.CompanyDoesNotExistsException;

import java.util.List;

public interface CompanyService
{
    List<Company> getAllCompanies();
    String updateCompany(CompanyDto companyDto,long id) throws CompanyDoesNotExistsException;
    String deleteCompany(long id) throws CompanyDoesNotExistsException;
    String updatePassword(PasswordDto passwordDto,long id) throws CompanyDoesNotExistsException;
}
