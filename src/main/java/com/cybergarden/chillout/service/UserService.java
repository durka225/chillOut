package com.cybergarden.chillout.service;

import com.cybergarden.chillout.dto.ProfileUserResponse;
import com.cybergarden.chillout.dto.PurchaseResponse;
import com.cybergarden.chillout.dto.RegDetailsRequest;
import com.cybergarden.chillout.model.User;
import com.cybergarden.chillout.model.UserDetails;
import com.cybergarden.chillout.repository.UserDetailsRepository;
import com.cybergarden.chillout.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public UserService(
            UserRepository userRepository,
            UserDetailsRepository userDetailsRepository
    ) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public ResponseEntity<?> isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty()
                ? ResponseEntity.ok().build()
                :  ResponseEntity.notFound().build();
    }

    @Transactional
    public synchronized ResponseEntity<?> newUsername(String username, String firebaseToken) {
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Такой пользователь уже существует");
        } else {
            userRepository.save(new User(username, firebaseToken));
            return ResponseEntity.ok().build();
        }
    }


    @Transactional
    public ResponseEntity<?> newUserDetails(String username,
                                            RegDetailsRequest request
    ) {
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.getUserByUsername(username);
            UserDetails userDetails = new UserDetails(
                    request.name(),
                    request.wages(),
                    0,
                    request.currentMoney(),
                    request.postpone(),
                    user
            );
            userDetailsRepository.save(userDetails);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<?> updateFirebaseToken(String username, String firebaseToken) {
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.getUserByUsername(username);
            user.setFirebaseToken(firebaseToken);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getUserProfile(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.getUserByUsername(username);
            UserDetails userDetails = userDetailsRepository.getUserDetailsByUser(user);
            List<PurchaseResponse> list = new ArrayList<>();
            user.getPurchases().forEach(it ->
                    list.add(new PurchaseResponse(
                                    it.getId().toString(),
                                    it.getName(),
                                    it.getCost(),
                                    it.getCategory().getName(),
                                    it.getDataLock(),
                                    it.getStatus()
                            )
                    )
            );
            return ResponseEntity.ok().body(new ProfileUserResponse(
                    user.getUsername(),
                    userDetails.getName(),
                    userDetails.getWages(),
                    userDetails.getSavingMoney(),
                    userDetails.getCurrentMoney(),
                    userDetails.getPostpone(),
                    list
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<?> updateUserDetailsFields(String username, String name, Integer wages, Integer postpone) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    UserDetails details = userDetailsRepository.getUserDetailsByUser(user);
                    if (details == null) {
                        return ResponseEntity.notFound().build();
                    }
                    if (name != null) {
                        details.setName(name);
                    }
                    if (wages != null) {
                        details.setWages(wages);
                    }
                    if (postpone != null) {
                        details.setPostpone(postpone);
                    }
                    userDetailsRepository.save(details);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<?> updateCurrentMoney(String username, Integer currentMoney) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    UserDetails details = userDetailsRepository.getUserDetailsByUser(user);
                    if (details == null) {
                        return ResponseEntity.notFound().build();
                    }
                    details.setCurrentMoney(currentMoney);
                    userDetailsRepository.save(details);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
