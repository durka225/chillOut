package com.cybergarden.chillout.repository;

import com.cybergarden.chillout.model.User;
import com.cybergarden.chillout.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
    UserDetails getUserDetailsByUser(User user);
}
