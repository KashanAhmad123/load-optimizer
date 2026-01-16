package com.logistics.load_optimizer.service;  // Changed from "package service;"

import com.logistics.load_optimizer.dto.LoadOptimizationRequest;  // Updated imports
import com.logistics.load_optimizer.dto.LoadOptimizationResponse;
import com.logistics.load_optimizer.mapper.DtoMapper;
import com.logistics.load_optimizer.model.LoadSolution;
import com.logistics.load_optimizer.model.Order;
import com.logistics.load_optimizer.model.Truck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Main service orchestrating the load optimization process
 * Follows the Service Layer pattern in MVC architecture
 */
@Service
public class LoadOptimizerService {

    private static final Logger logger = LoggerFactory.getLogger(LoadOptimizerService.class);

    private final DtoMapper dtoMapper;
    private final OptimizationEngine optimizationEngine;

    public LoadOptimizerService(DtoMapper dtoMapper, OptimizationEngine optimizationEngine) {
        this.dtoMapper = dtoMapper;
        this.optimizationEngine = optimizationEngine;
    }

    /**
     * Main business logic: Optimize load assignment
     *
     * @param request The optimization request containing truck and orders
     * @return The optimized load solution
     */
    @Cacheable(value = "optimizedLoad",key = "#request.hashcode()")
    public LoadOptimizationResponse optimizeLoad(LoadOptimizationRequest request) {
        logger.info("Starting load optimization for truck: {}", request.getTruck().getId());

        long startTime = System.currentTimeMillis();

        // Step 1: Convert DTOs to domain models
        Truck truck = dtoMapper.toTruckModel(request.getTruck());
        List<Order> orders = dtoMapper.toOrderModels(request.getOrders());

        logger.debug("Processing {} orders for truck with capacity: {}lbs, {}cuft",
                orders.size(), truck.getMaxWeightLbs(), truck.getMaxvolumeCuft());

        // Step 2: Run optimization algorithm
        LoadSolution solution = optimizationEngine.optimize(truck, orders);

        // Step 3: Convert solution to response DTO
        LoadOptimizationResponse response = dtoMapper.toResponseDto(solution);

        long endTime = System.currentTimeMillis();
        logger.info("Optimization completed in {}ms. Selected {} orders with total payout: ${}",
                (endTime - startTime), solution.getOrderCount(),
                solution.getTotalPayoutCents() / 100.0);

        return response;
    }
}