package com.hrd.repository;

import com.hrd.entity.PublicHolidayEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHolidayEntity, Long> {

    @Transactional
    @Query(value = "SELECT * FROM public_holidays WHERE holiday_name = ?1", nativeQuery = true)
    PublicHolidayEntity findByName(String name);
}
