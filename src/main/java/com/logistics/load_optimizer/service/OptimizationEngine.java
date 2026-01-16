package com.logistics.load_optimizer.service;  // Changed from "package service;"

import com.logistics.load_optimizer.model.LoadSolution;  // Updated imports
import com.logistics.load_optimizer.model.Order;
import com.logistics.load_optimizer.model.Truck;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Core optimization engine using Dynamic Programming with bitmask
 */
@Service
public class OptimizationEngine {

    private final CompatibilityValidator validator;

    public OptimizationEngine(CompatibilityValidator validator) {
        this.validator = validator;
    }

    /**
     * Internal class to represent a DP state
     */
    private static class DPState {
        long mask;
        long payout;
        int weight;
        int volume;
        List<Integer> orderIndices;

        DPState(long mask, long payout, int weight, int volume, List<Integer> orderIndices) {
            this.mask = mask;
            this.payout = payout;
            this.weight = weight;
            this.volume = volume;
            this.orderIndices = new ArrayList<>(orderIndices);
        }
    }

    /**
     * Find optimal order combination using Dynamic Programming
     */
    public LoadSolution optimize(Truck truck, List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return new LoadSolution(truck.getId(), truck.getMaxWeightLbs(), truck.getMaxvolumeCuft());
        }

        // Filter valid orders first
        List<Integer> validIndices = getValidOrderIndices(orders, truck);

        if (validIndices.isEmpty()) {
            return new LoadSolution(truck.getId(), truck.getMaxWeightLbs(), truck.getMaxvolumeCuft());
        }

        // Choose algorithm based on problem size
        if (validIndices.size() <= 20) {
            return optimizeWithDynamicProgramming(truck, orders, validIndices);
        } else {
            return optimizeWithGreedyHeuristic(truck, orders, validIndices);
        }
    }

    /**
     * Dynamic Programming with bitmask for optimal solution (n <= 20)
     */
    private LoadSolution optimizeWithDynamicProgramming(Truck truck, List<Order> orders,
                                                        List<Integer> validIndices) {
        int n = validIndices.size();
        long totalStates = 1L << n;

        Map<Long, DPState> dp = new HashMap<>();
        dp.put(0L, new DPState(0L, 0L, 0, 0, new ArrayList<>()));

        DPState bestState = dp.get(0L);

        // Iterate through all possible states
        for (long mask = 0; mask < totalStates; mask++) {
            if (!dp.containsKey(mask)) continue;

            DPState currentState = dp.get(mask);
            List<Order> currentOrders = getOrdersFromIndices(currentState.orderIndices, orders);

            // Try adding each unused order
            for (int i = 0; i < n; i++) {
                if ((mask & (1L << i)) != 0) continue; // Already selected

                int orderIdx = validIndices.get(i);
                Order candidate = orders.get(orderIdx);

                // Check capacity constraints
                if (validator.wouldExceedCapacity(candidate, currentState.weight,
                        currentState.volume, truck)) {
                    continue;
                }

                // Check compatibility with current selection
                if (!validator.isCompatibleWithSelection(candidate, currentOrders)) {
                    continue;
                }

                // Create new state
                long newMask = mask | (1L << i);
                long newPayout = currentState.payout + candidate.getPayoutCents();
                int newWeight = currentState.weight + candidate.getWeightLbs();
                int newVolume = currentState.volume + candidate.getVolumeCuft();

                List<Integer> newIndices = new ArrayList<>(currentState.orderIndices);
                newIndices.add(orderIdx);

                DPState newState = new DPState(newMask, newPayout, newWeight, newVolume, newIndices);

                // Update DP table if this is a better solution
                if (!dp.containsKey(newMask) || dp.get(newMask).payout < newPayout) {
                    dp.put(newMask, newState);

                    if (newPayout > bestState.payout) {
                        bestState = newState;
                    }
                }
            }
        }

        return buildSolution(truck, orders, bestState);
    }

    /**
     * Greedy heuristic for larger problem instances (n > 20)
     */
    private LoadSolution optimizeWithGreedyHeuristic(Truck truck, List<Order> orders,
                                                     List<Integer> validIndices) {
        // Sort by efficiency ratio (payout per resource unit)
        List<Integer> sortedIndices = new ArrayList<>(validIndices);
        sortedIndices.sort((a, b) -> {
            Order o1 = orders.get(a);
            Order o2 = orders.get(b);
            return Double.compare(o2.efficiencyRatio(), o1.efficiencyRatio());
        });

        List<Integer> selectedIndices = new ArrayList<>();
        List<Order> selectedOrders = new ArrayList<>();
        int totalWeight = 0;
        int totalVolume = 0;
        long totalPayout = 0L;

        for (int idx : sortedIndices) {
            Order order = orders.get(idx);

            if (!validator.wouldExceedCapacity(order, totalWeight, totalVolume, truck) &&
                    validator.isCompatibleWithSelection(order, selectedOrders)) {

                selectedIndices.add(idx);
                selectedOrders.add(order);
                totalWeight += order.getWeightLbs();
                totalVolume += order.getVolumeCuft();
                totalPayout += order.getPayoutCents();
            }
        }

        DPState greedyState = new DPState(0L, totalPayout, totalWeight, totalVolume, selectedIndices);
        return buildSolution(truck, orders, greedyState);
    }

    /**
     * Get indices of all valid orders
     */
    private List<Integer> getValidOrderIndices(List<Order> orders, Truck truck) {
        List<Integer> validIndices = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (validator.isValidOrder(orders.get(i), truck)) {
                validIndices.add(i);
            }
        }
        return validIndices;
    }

    /**
     * Convert indices to Order objects
     */
    private List<Order> getOrdersFromIndices(List<Integer> indices, List<Order> allOrders) {
        List<Order> result = new ArrayList<>();
        for (int idx : indices) {
            result.add(allOrders.get(idx));
        }
        return result;
    }

    /**
     * Build LoadSolution from DP state
     */
    private LoadSolution buildSolution(Truck truck, List<Order> orders, DPState state) {
        LoadSolution solution = new LoadSolution(
                truck.getId(),
                truck.getMaxWeightLbs(),
                truck.getMaxvolumeCuft()
        );

        for (int idx : state.orderIndices) {
            solution.addOrder(orders.get(idx));
        }

        return solution;
    }
}