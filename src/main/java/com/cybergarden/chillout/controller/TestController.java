package com.cybergarden.chillout.controller;

import com.cybergarden.chillout.service.FirebaseNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    private final FirebaseNotificationService firebaseNotificationService;

    public TestController(FirebaseNotificationService firebaseNotificationService) {
        this.firebaseNotificationService = firebaseNotificationService;
    }

    @PostMapping
    public void test(
            @RequestHeader("username") String username
    ) {
        firebaseNotificationService.sendNotification(username, "Test Title", "Test Body");
    }
}
