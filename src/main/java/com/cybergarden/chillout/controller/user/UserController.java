package com.cybergarden.chillout.controller.user;

import com.cybergarden.chillout.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/details")
    public ResponseEntity<?> updateDetails(
            @RequestHeader("username") String username,
            @RequestBody UpdateDetailsRequest request
    ) {
        return userService.updateUserDetailsFields(username, request.name(), request.wages(), request.postpone());
    }

    @PutMapping("/money")
    public ResponseEntity<?> updateMoney(
            @RequestHeader("username") String username,
            @RequestBody UpdateMoneyRequest request
    ) {
        return userService.updateCurrentMoney(username, request.currentMoney());
    }

    public static record UpdateDetailsRequest(String name, Integer wages, Integer postpone) {}
    public static record UpdateMoneyRequest(Integer currentMoney) {}
}
