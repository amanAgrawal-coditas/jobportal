package com.portal.repository;

import com.portal.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Jobs, Long> {
    Optional<Jobs> findByJobTitle(String jobTitle);
    Optional<Jobs> findBySalary(double salary);
    Optional<Jobs> findByLocation(String locationName);
    Optional<Jobs> findByCompany(String company);
    Optional<Jobs> findByCategory(String category);
    Optional<Jobs> findByJobTitleOrSalaryOrLocationOrCompanyOrCategory(String jobTitle,double salary,String location,String company,String category);


}
