package com.cybergarden.chillout.service;

import com.cybergarden.chillout.dto.ProfileUserResponse;
import com.cybergarden.chillout.dto.RegDetailsRequest;
import com.cybergarden.chillout.model.User;
import com.cybergarden.chillout.model.UserDetails;
import com.cybergarden.chillout.repository.UserDetailsRepository;
import com.cybergarden.chillout.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
                    request.savingMoney(),
                    request.wages(),
                    request.currentMoney(),
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
            return ResponseEntity.ok().body(new ProfileUserResponse(
                    user.getUsername(),
                    userDetails.getName(),
                    userDetails.getWages(),
                    userDetails.getSavingMoney(),
                    userDetails.getCurrentMoney(),
                    user.getPurchases()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
