package com.nox.JavaBootCampAdv.controller;

import com.nox.JavaBootCampAdv.dto.PositionDto;
import com.nox.JavaBootCampAdv.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/")
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping("/")
    public ResponseEntity<PositionDto> createPosition(@RequestBody PositionDto position) {
        return ResponseEntity.ok(positionService.createPosition(position));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable Long id, @RequestBody PositionDto position) {
        return ResponseEntity.ok(positionService.updatePosition(id, position));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<PositionDto>> getPositionsByCompany(Long id) {
        return ResponseEntity.ok(positionService.getPositionsByCompany(id));
    }

    @GetMapping("/generate")
    public ResponseEntity<List<PositionDto>> generatePositions(@RequestParam int count) {
        return ResponseEntity.ok(positionService.generatePositions(count, null));
    }
}