package com.portal.repository;

import com.portal.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Jobs, Long>
{
    Optional<Jobs> findByJobTitle(String jobTitle);
    Optional<Jobs> findBySalary(double salary);
    Optional<Jobs> findByLocation(String name);

}
