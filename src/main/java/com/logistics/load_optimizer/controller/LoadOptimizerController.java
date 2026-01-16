package com.logistics.load_optimizer.controller;

import com.logistics.load_optimizer.dto.LoadOptimizationRequest;  // Updated imports
import com.logistics.load_optimizer.dto.LoadOptimizationResponse;
import com.logistics.load_optimizer.service.LoadOptimizerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {

    private static final Logger logger = LoggerFactory.getLogger(LoadOptimizerController.class);

    private final LoadOptimizerService loadOptimizerService;

    public LoadOptimizerController(LoadOptimizerService loadOptimizerService) {
        this.loadOptimizerService = loadOptimizerService;
    }


    @PostMapping("/optimize")
    public ResponseEntity<LoadOptimizationResponse> optimizeLoad(
            @Valid @RequestBody LoadOptimizationRequest request) {

        logger.info("Received optimization request for truck: {}", request.getTruck().getId());

        try {
            LoadOptimizationResponse response = loadOptimizerService.optimizeLoad(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error processing optimization request", e);
            throw e;
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Load Optimizer Service is running");
    }
}