package com.cybergarden.chillout.service;

import com.cybergarden.chillout.dto.CoolingPeriodRequest;
import com.cybergarden.chillout.dto.CoolingPeriodResponse;
import com.cybergarden.chillout.model.CoolingPeriod;
import com.cybergarden.chillout.model.User;
import com.cybergarden.chillout.repository.CoolingPeriodRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoolingPeriodService {

    private final CoolingPeriodRepository repository;
    private final UserService userService;

    public CoolingPeriodService(CoolingPeriodRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public ResponseEntity<?> addPeriod(String username, CoolingPeriodRequest request) {
        User user = userService.getUserByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();

        CoolingPeriod period = new CoolingPeriod(
                request.minPrice(),
                request.maxPrice(),
                request.durationDays(),
                user
        );
        repository.save(period);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getPeriods(String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();

        List<CoolingPeriodResponse> response = repository.findAllByUser(user).stream()
                .map(p -> new CoolingPeriodResponse(p.getId(), p.getMinPrice(), p.getMaxPrice(), p.getDurationDays()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deletePeriod(UUID id, String username) {
        User user = userService.getUserByUsername(username);
        return repository.findById(id)
                .filter(p -> p.getUser().equals(user))
                .map(p -> {
                    repository.delete(p);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public LocalDate calculateDataLock(User user, Integer price) {
        List<CoolingPeriod> periods = repository.findAllByUser(user);
        int daysToAdd = 0;

        for (CoolingPeriod period : periods) {
            int min = period.getMinPrice() != null ? period.getMinPrice() : 0;
            int max = period.getMaxPrice() != null ? period.getMaxPrice() : Integer.MAX_VALUE;

            if (price >= min && price < max) {
                // Если нашли подходящий диапазон, берем его длительность
                // Можно добавить логику выбора максимального, если диапазоны пересекаются
                daysToAdd = period.getDurationDays();
                break; // Предполагаем, что диапазоны не пересекаются или берем первый попавшийся
            }
        }
        
        // Если дней 0, то блокировка до сегодняшнего дня (доступно сразу) или +0 дней
        return LocalDate.now().plusDays(daysToAdd);
    }
}