package com.cybergarden.chillout.controller.user;

import com.cybergarden.chillout.dto.CoolingPeriodRequest;
import com.cybergarden.chillout.service.CoolingPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cooling-periods")
public class CoolingPeriodController {

    private final CoolingPeriodService service;

    public CoolingPeriodController(CoolingPeriodService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addPeriod(@RequestHeader("username") String username, @RequestBody CoolingPeriodRequest request) {
        return service.addPeriod(username, request);
    }

    @GetMapping
    public ResponseEntity<?> getPeriods(@RequestHeader("username") String username) {
        return service.getPeriods(username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeriod(@RequestHeader("username") String username, @PathVariable UUID id) {
        return service.deletePeriod(id, username);
    }
}