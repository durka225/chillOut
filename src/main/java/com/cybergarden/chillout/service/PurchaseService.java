package com.cybergarden.chillout.service;

import com.cybergarden.chillout.dto.NewPurchaseRequest;
import com.cybergarden.chillout.dto.PurchaseResponse;
import com.cybergarden.chillout.dto.Status;
import com.cybergarden.chillout.model.Category;
import com.cybergarden.chillout.model.Purchases;
import com.cybergarden.chillout.model.User;
import com.cybergarden.chillout.model.UserDetails;
import com.cybergarden.chillout.repository.PurchaseRepository;
import com.cybergarden.chillout.repository.UserDetailsRepository;
import com.cybergarden.chillout.repository.UserRepository;
import com.cybergarden.chillout.service.CategoryService;
import com.cybergarden.chillout.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    private final UserDetailsRepository userDetailsRepository;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            UserService userService,
            CategoryService categoryService,
            UserDetailsRepository userDetailsRepository
    ) {
        this.purchaseRepository = purchaseRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.userDetailsRepository = userDetailsRepository;
    }

    public ResponseEntity<?> newPurchase(String username, NewPurchaseRequest request) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            Category category = categoryService.findCategoryByName(request.categoryName());
            if (category != null) {
                purchaseRepository.save(new Purchases(
                        request.name(),
                        request.price(),
                        request.dataLock(),
                        request.notificationTime(),
                        category,
                        user,
                        Status.valueOf(request.status().toUpperCase())
                ));
                return ResponseEntity.status(HttpStatus.CREATED).body("Purchase created successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> getPurchase(String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            List<Purchases> list = purchaseRepository.findPurchasesByUser(user);
            List<PurchaseResponse> responses = new ArrayList<>();
            list.forEach(purchase -> {
                responses.add(new PurchaseResponse(
                        purchase.getId().toString(),
                        purchase.getName(),
                        purchase.getCost(),
                        purchase.getCategory().getName(),
                        purchase.getDataLock(),
                        purchase.getStatus()
                ));
            });
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> delPurchase(UUID uuid, String username) {
        User user = userService.getUserByUsername(username);
        Purchases purchases = purchaseRepository.getPurchasesById(uuid);
        if (purchases.getUser().equals(user)) {
            purchaseRepository.delete(purchases);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    public ResponseEntity<?> switchStatus(UUID uuid, String status, String username) {
        User user = userService.getUserByUsername(username);
        Purchases purchases = purchaseRepository.getPurchasesById(uuid);
        if (purchases.getUser().equals(user)) {
            purchases.setStatus(Status.valueOf(status.toUpperCase()));
            purchaseRepository.save(purchases);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Transactional
    public ResponseEntity<?> cancelPurchase(UUID uuid, String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();

        Purchases purchases = purchaseRepository.getPurchasesById(uuid);
        if (purchases == null) return ResponseEntity.notFound().build();

        if (!purchases.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (purchases.getStatus() == Status.CANCELED || purchases.getStatus() == Status.PURCHASED) {
            return ResponseEntity.badRequest().body("Cannot change purchase with status PURCHASED or CANCELED");
        }

        // пометить отменённой
        purchases.setStatus(Status.CANCELED);
        purchaseRepository.save(purchases);

        // добавить стоимость в savingMoney
        UserDetails details = userDetailsRepository.getUserDetailsByUser(user);
        if (details == null) return ResponseEntity.notFound().build();
        details.setSavingMoney(details.getSavingMoney() + purchases.getCost());
        userDetailsRepository.save(details);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> buyPurchase(UUID uuid, String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();

        Purchases purchases = purchaseRepository.getPurchasesById(uuid);
        if (purchases == null) return ResponseEntity.notFound().build();

        if (!purchases.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (purchases.getStatus() == Status.CANCELED || purchases.getStatus() == Status.PURCHASED) {
            return ResponseEntity.badRequest().body("Cannot change purchase with status PURCHASED or CANCELED");
        }

        UserDetails details = userDetailsRepository.getUserDetailsByUser(user);
        if (details == null) return ResponseEntity.notFound().build();
        details.setCurrentMoney(details.getCurrentMoney() - purchases.getCost());
        userDetailsRepository.save(details);

        purchases.setStatus(Status.PURCHASED);
        purchaseRepository.save(purchases);

        return ResponseEntity.ok().build();
    }
}
