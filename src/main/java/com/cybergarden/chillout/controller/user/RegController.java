package com.cybergarden.chillout.controller.user;

import com.cybergarden.chillout.dto.RegDetailsRequest;
import com.cybergarden.chillout.dto.RegUsernameRequest;
import com.cybergarden.chillout.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RegController {

    private final UserService userService;

    public RegController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/step1")
    public ResponseEntity<?> registerUsername(@RequestBody RegUsernameRequest request) {
        return userService.newUsername(request.username());
    }

    @PostMapping("/step2")
    public ResponseEntity<?> registerUser(@RequestBody RegDetailsRequest request,
                                          @RequestHeader("username") String username) {
        return userService.newUserDetails(username, request);
    }
}
