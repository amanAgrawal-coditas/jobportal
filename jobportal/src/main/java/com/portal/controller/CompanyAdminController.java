package com.portal.controller;

import com.portal.entity.Company;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.service.AuthService;
import com.portal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companyAdmin")
public class CompanyAdminController
{
    @Autowired
    private CompanyService companyService;

    @GetMapping("/listAllCompanies")
    List<Company> getAll()
    {
        return companyService.getAllCompanies();
    }
    @DeleteMapping("deleteCompany/{id}")
    String deleteCompany(@PathVariable long id) throws CompanyDoesNotExistsException
    {
        return companyService.deleteCompany(id);
    }


}
