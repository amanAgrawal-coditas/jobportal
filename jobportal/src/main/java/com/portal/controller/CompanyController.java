package com.portal.controller;

import com.portal.dto.CompanyDto;
import com.portal.dto.JobDto;
import com.portal.dto.PasswordDto;
import com.portal.exception.CategoryNotFoundException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.service.CompanyService;
import com.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController
{
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @PutMapping("/update/{id}")
    ResponseEntity<String> updateCompanyDetails(@RequestBody CompanyDto companyDto, @PathVariable long id) throws CompanyDoesNotExistsException
    {
        String response=companyService.updateCompany(companyDto,id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PutMapping("/updatePassword/{id}")
    ResponseEntity<String> updatePassword(@RequestBody PasswordDto passwordDto,@PathVariable long id) throws CompanyDoesNotExistsException
    {
        String response=companyService.updatePassword(passwordDto,id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/addJob/{companyId}")
    public ResponseEntity<String> addJob(@RequestBody JobDto jobDto,@PathVariable long companyId,@PathVariable long categoryId) throws CompanyDoesNotExistsException, CategoryNotFoundException
    {
        String response=jobService.addJob(jobDto,companyId,categoryId);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);

    }
}
