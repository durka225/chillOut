package com.cybergarden.chillout.service;

import com.cybergarden.chillout.model.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {

    private final UserService userService;

    public FirebaseNotificationService(UserService userService) {
        this.userService = userService;
    }

    public void sendNotification(String username, String title, String body) {
        User user = userService.getUserByUsername(username);
        String token = user.getFirebaseToken();
        if (token == null || token.isEmpty()) {
            return;
        }

        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            System.err.println("Error sending firebase notification: " + e.getMessage());
        }
    }
}