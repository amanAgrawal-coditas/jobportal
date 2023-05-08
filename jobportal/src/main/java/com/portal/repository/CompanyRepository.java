package com.portal.repository;

import com.portal.entity.Candidate;
import com.portal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long>
{
    Optional<Candidate> findByEmail(String email);
    Boolean existsByEmail(String email);

}
