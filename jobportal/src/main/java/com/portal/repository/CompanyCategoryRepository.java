package com.portal.repository;

import com.portal.entity.CompanyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, Long>
{
}