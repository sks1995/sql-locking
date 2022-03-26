package com.practice.locking.sqllocking.persistence.repository;

import com.practice.locking.sqllocking.persistence.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, String> {

}
