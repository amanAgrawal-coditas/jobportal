package com.portal.repository;

import com.portal.entity.AppliedApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppliedApplicationRepository extends JpaRepository<AppliedApplication,Long> {
}
