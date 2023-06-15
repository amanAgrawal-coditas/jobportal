package com.portal.controller;

import com.portal.request.CompanyDto;
import com.portal.request.JobDto;
import com.portal.request.PasswordDto;
import com.portal.exception.CategoryNotFoundException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.exception.JobNotFoundException;
import com.portal.response.AppliedApplicationResponse;
import com.portal.response.CompanyResponse;
import com.portal.response.JobResponse;
import com.portal.service.CompanyService;
import com.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @PutMapping("/update/{id}")
    ResponseEntity<String> updateCompanyDetails(@RequestBody CompanyDto companyDto, @PathVariable long id) throws CompanyDoesNotExistsException
    {
        String response = companyService.updateCompany(companyDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PutMapping("/updatePassword/{id}")
    ResponseEntity<String> updatePassword(@RequestBody PasswordDto passwordDto, @PathVariable long id) throws CompanyDoesNotExistsException {
        String response = companyService.updatePassword(passwordDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addJob/{companyId}/{categoryId}")
    public ResponseEntity<String> addJob(@RequestBody JobDto jobDto, @PathVariable long companyId, @PathVariable long categoryId) throws CompanyDoesNotExistsException, CategoryNotFoundException {
        String response = jobService.addJob(jobDto, companyId, categoryId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }
    @PutMapping("updateJob/{companyId}/{categoryId}/{jobId}")
    public ResponseEntity<String> updateJob(@RequestBody JobDto jobDto, @PathVariable long companyId, @PathVariable long categoryId, @PathVariable long jobId) throws CompanyDoesNotExistsException, CategoryNotFoundException, JobNotFoundException {
        String response = jobService.updateJob(jobDto, companyId, categoryId, jobId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("deleteJob/{companyId}/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable long companyId, @PathVariable long jobId) throws JobNotFoundException
    {
        String response = jobService.deleteJob(companyId, jobId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getAllJobsByCompany/{companyId}")
    public List<JobResponse> getAllJobsByCompanies(@PathVariable Long companyId)
    {
        return jobService.getAllJobsForCompany(companyId);
    }
    @GetMapping("/getCompanyDetails")
    public CompanyResponse getDetails(long id)
    {
        return companyService.getCompanyById(id);
    }
    @GetMapping("/getApplicationList/{jobId}")
    List<AppliedApplicationResponse>getAll(@PathVariable long jobId)
    {
        return companyService.getCandidatesByJob(jobId);
    }
    @GetMapping("/CandidateAccepted/{jobId}/{candidateId}")
    String candidateAccepted(@PathVariable long jobId,@PathVariable long candidateId)
    {
        return companyService.candidateAccepted(jobId, candidateId);
    }
    @GetMapping("/CandidateRejected/{jobId}/{candidateId}")
    String candidateRejected(@PathVariable long jobId,@PathVariable long candidateId)
    {
        return companyService.candidateRejected(jobId,candidateId);
    }
}
