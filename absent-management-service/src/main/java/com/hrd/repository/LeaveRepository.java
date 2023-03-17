package com.hrd.repository;

import com.hrd.entity.LeavePermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeavePermit, Long> {
}
