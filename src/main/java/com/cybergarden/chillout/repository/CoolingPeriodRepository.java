package com.cybergarden.chillout.repository;

import com.cybergarden.chillout.model.CoolingPeriod;
import com.cybergarden.chillout.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CoolingPeriodRepository extends JpaRepository<CoolingPeriod, UUID> {
    List<CoolingPeriod> findAllByUser(User user);
}