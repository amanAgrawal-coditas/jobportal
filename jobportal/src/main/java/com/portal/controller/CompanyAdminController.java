package com.portal.controller;

import com.portal.exception.CompanyDoesNotExistsException;

import com.portal.response.CompanyResponse;

import com.portal.service.CategoryService;
import com.portal.service.CompanyService;
import com.portal.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companyAdmin")
public class CompanyAdminController
{
    @Autowired
    private CompanyService companyService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCompanies")
    List<CompanyResponse> getAll()
    {
        return companyService.getAllCompanies();
    }
    @DeleteMapping("deleteCompany/{id}")
    String deleteCompany(@PathVariable long id) throws CompanyDoesNotExistsException
    {
        return companyService.deleteCompany(id);
    }
    @PostMapping("/addLocation")
    ResponseEntity<Boolean> addLocation(String location)
    {
        Boolean response=locationService.addLocation(location);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/deleteLocation")
    ResponseEntity<Boolean>deleteLocation(int id)
    {
        Boolean response=locationService.deleteLocation(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/addCategory")
    ResponseEntity<Boolean> addCategory(String category)
    {
        Boolean response=categoryService.addCategory(category);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/deleteCategory")
    ResponseEntity<Boolean>deleteCategory(int id)
    {
        Boolean response=categoryService.deleteCategory(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
