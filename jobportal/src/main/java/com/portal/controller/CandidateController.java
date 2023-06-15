package com.portal.controller;

import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.request.ApplyJobDto;
import com.portal.request.CandidateDto;
import com.portal.request.PasswordDto;
import com.portal.exception.CandidateDoesNotExistsException;
import com.portal.exception.JobNotFoundException;
import com.portal.response.JobResponse;
import com.portal.response.Response;
import com.portal.service.ApplyJobService;
import com.portal.service.CandidateService;
import com.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CandidateController
{
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ApplyJobService applyJobService;
    @Autowired
    private JobService jobService;
    @PutMapping(value = "/updateCandidate/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCandidate(@RequestBody CandidateDto candidateDto,@PathVariable long id) throws CandidateDoesNotExistsException
    {
       return candidateService.updateCandidate(candidateDto,id);
    }
    @PutMapping("/updatePassword/{id}")
    public String updatePassword(@PathVariable long id, @RequestBody PasswordDto passwordDto) throws CandidateDoesNotExistsException
    {
        return candidateService.updatePassword(passwordDto,id);
    }
    @PostMapping("/applyJob/{candidateId}/{jobId}/{companyId}")
    ResponseEntity<String>applyJob(@PathVariable long jobId, @PathVariable long candidateId, @RequestBody ApplyJobDto applyJobDto,@PathVariable long companyId) throws JobNotFoundException, CandidateDoesNotExistsException, CompanyDoesNotExistsException {
        String response=applyJobService.applyJob(applyJobDto,candidateId,jobId,companyId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/findJobsByCity/{location}")
    List<JobResponse> findJobsByCity(@PathVariable String location)
    {
        return applyJobService.findJobsByCity(location);
    }
    @GetMapping("/findJobsBySalary")
    List<JobResponse>findJobsBySalary(double salary)
    {
        return applyJobService.findJobsBySalary(salary);
    }
    @GetMapping("/findJobsByCompany/{company}")
    List<JobResponse>findJobsByCompany(@PathVariable String company)
    {
        return applyJobService.findJobByCompany(company);
    }
    @GetMapping("/findJobsByCategory/{category}")
    List<JobResponse>findJobsByCategory(@PathVariable String category)
    {
        return applyJobService.findJobByCategory(category);
    }
    @GetMapping("/getDetails/{email}")
    ResponseEntity<Response>candidateDetails(@PathVariable String email) throws CandidateDoesNotExistsException
    {
        Response response=candidateService.getCandidate(email);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/findJobsByTitle/{title}")
    List<JobResponse>findJobsByTitle(@PathVariable String title)
    {
        return applyJobService.findJobsByTitle(title);
    }
    @GetMapping("/getAllJobs")
    public List<JobResponse> getAllJobs()
    {
        return jobService.getAllJobs();
    }
    @GetMapping("/findByJobTitleOrSalaryOrLocationOrCompanyOrCategory/{jobTitle}/{salary}/{location}/{company}/{category}")
    List<JobResponse>findByJobTitleOrSalaryOrLocationOrCompanyOrCategory(@RequestParam String jobTitle, @RequestParam double salary, @RequestParam String location,
                                                                         @RequestParam String company, @RequestParam String category)
    {
        return applyJobService.findByJobTitleOrSalaryOrLocationOrCompanyOrCategory(jobTitle,salary,location,company,category);
    }


}