package com.cybergarden.chillout.repository;

import com.cybergarden.chillout.model.Purchases;
import com.cybergarden.chillout.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchases, UUID> {
    Object getPurchasesByUser(User user);

    List<Purchases> findPurchasesByUser(User user);
}
