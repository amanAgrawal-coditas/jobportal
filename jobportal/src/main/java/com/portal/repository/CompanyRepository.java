package com.portal.repository;

import com.portal.entity.Candidate;
import com.portal.entity.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company,Long>
{

    Optional<Company> findByEmail(String email);
    Boolean existsByEmail(String email);

}
