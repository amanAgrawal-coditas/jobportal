package com.portal.service.implementation;

import com.portal.dto.JobDto;
import com.portal.entity.Category;
import com.portal.entity.Company;
import com.portal.entity.Jobs;
import com.portal.entity.Location;
import com.portal.exception.CategoryNotFoundException;
import com.portal.exception.CompanyDoesNotExistsException;
import com.portal.exception.JobNotFoundException;
import com.portal.repository.CategoryRepository;
import com.portal.repository.CompanyRepository;
import com.portal.repository.JobRepository;
import com.portal.repository.LocationRepository;
import com.portal.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImplementation implements JobService
{
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public String addJob(JobDto jobDto, long companyId,long categoryId) throws CompanyDoesNotExistsException, CategoryNotFoundException
    {
        Company company=companyRepository.findById(companyId).orElseThrow(()->new CompanyDoesNotExistsException(HttpStatus.BAD_REQUEST,"The company does not exists"));
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException(HttpStatus.BAD_REQUEST,"This category does not exists in this company"));
        List<Location>locationList=jobDto.getAddNewLocations().stream().map(id -> locationRepository.findById(id).get()).collect(Collectors.toList());
        Jobs jobs=new Jobs();
        jobs.setCategory(category);
        jobs.setCompany(company);
        jobs.setJobDescription(jobDto.getJobDescription());
        jobs.setJobTitle(jobDto.getJobTitle());
        jobs.setLocation(locationList);
        jobs.setSalary(jobDto.getSalary());
        jobRepository.save(jobs);
        return "Job posted!";
    }

    @Override
    public String updateJob(JobDto jobDto, long companyId, long categoryId,long jobId) throws CompanyDoesNotExistsException, CategoryNotFoundException, JobNotFoundException {
        Company company=companyRepository.findById(companyId).orElseThrow(()->new CompanyDoesNotExistsException(HttpStatus.BAD_REQUEST,"The company does not exists"));
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException(HttpStatus.BAD_REQUEST,"This category does not exists in this company"));
        Jobs jobs=jobRepository.findById(jobId).orElseThrow(()->new JobNotFoundException(HttpStatus.BAD_REQUEST,"The job does not exist"));
        if (jobDto.getJobDescription()!=null&&jobs.getJobDescription()!= jobDto.getJobDescription())
        {
            jobs.setJobDescription(jobDto.getJobDescription());
        }
        if (jobDto.getJobTitle() != null&&jobs.getJobTitle()!=jobDto.getJobTitle())
        {
            jobs.setJobTitle(jobDto.getJobTitle());
        }
        if(jobDto.getSalary()!=0&&jobs.getSalary()!= jobDto.getSalary())
        {
            jobs.setSalary(jobDto.getSalary());
        }
        if (jobDto.getCategory()!=null && jobs.getCategory()!=category)
        {
            jobs.setCategory(jobDto.getCategory());
        }
        jobs.setLocation(jobDto.getAddNewLocations().stream().map(location -> locationRepository.findById(location).get()).collect(Collectors.toList()));
        List<Location> locationList = jobs.getLocation();
        jobs.setLocation(locationList.stream()
                .filter(location -> jobDto.getRemoveLocations()
                        .stream().anyMatch(locationId -> locationId != (location.getLocationId()))
                ).collect(Collectors.toList()));
        jobRepository.save(jobs);
        return "Job has been updated";
    }

    @Override
    public String deleteJob(long jobId, long companyId) throws JobNotFoundException {
        Jobs jobs=jobRepository.findById(jobId).orElseThrow(()->new JobNotFoundException(HttpStatus.BAD_REQUEST,"The job does not exist in the particular company"));
        jobRepository.delete(jobs);
        return "Deleted successfully";
    }
}
