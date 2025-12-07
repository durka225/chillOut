package com.cybergarden.chillout.controller.purchase;

import com.cybergarden.chillout.dto.NewPurchaseRequest;
import com.cybergarden.chillout.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase/new")
    public ResponseEntity<?> purchase(
            @RequestBody NewPurchaseRequest request,
            @RequestHeader("username") String username
    ) {
        return purchaseService.newPurchase(username, request);
    }

    @Operation(
            summary = "Получить все покупки пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Покупки успешно получены",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                                [
                                                  {
                                                    "name": "Кофе",
                                                    "price": 150,
                                                    "categoryName": "coffee",
                                                    "dataLock": "2025-12-07"
                                                  }
                                                ]
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")

            }
    )
    @GetMapping("/purchase")
    public ResponseEntity<?> getPurchase(
            @RequestHeader("username") String username
    ) {
        return purchaseService.getPurchase(username);
    }

    @DeleteMapping("/purchase/delete")
    public ResponseEntity<?> deletePurchase(
            @RequestParam UUID uuid,
            @RequestHeader("username") String username
    ) {
        return purchaseService.delPurchase(uuid, username);
    }

    @PutMapping("/purchase/switchStatus")
    public ResponseEntity<?> switchStatus(
            @RequestParam UUID uuid,
            @RequestParam String status,
            @RequestHeader("username") String username
    ) {
        return purchaseService.switchStatus(uuid, status, username);
    }

    @PutMapping("/purchase/cancel")
    public ResponseEntity<?> cancelPurchase(
            @RequestParam UUID uuid,
            @RequestHeader("username") String username
    ) {
        return purchaseService.cancelPurchase(uuid, username);
    }

    @PutMapping("/purchase/buy")
    public ResponseEntity<?> buyPurchase(
            @RequestParam UUID uuid,
            @RequestHeader("username") String username
    ) {
        return purchaseService.buyPurchase(uuid, username);
    }
}
