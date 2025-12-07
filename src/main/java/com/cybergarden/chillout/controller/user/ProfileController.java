package com.cybergarden.chillout.controller.user;

import com.cybergarden.chillout.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Получить профиль пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Профиль успешно получен",
                            content = @Content(
                                    examples = @ExampleObject(value =
                                            "{ \"username\": \"CyberGarden\", " +
                                            "\"name\": \"John\", " +
                                            "\"wages\": 5000, " +
                                            "\"savingMoney\": 2000, " +
                                            "\"currentMoney\": 3000, " +
                                            "\"purchases\": [] }")
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")

            }
    )
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader("username") String username
    ) {
       return userService.getUserProfile(username);
    }

    @PutMapping("/profile/firebaseToken")
    public ResponseEntity<?> updateFirebaseToken(
            @RequestHeader("username") String username,
            @RequestHeader("firebaseToken") String firebaseToken
    ) {
            return userService.updateFirebaseToken(username, firebaseToken);
    }

}
