package com.logistics.load_optimizer.service;  // Changed from "package service;"

import com.logistics.load_optimizer.model.Order;  // Updated imports
import com.logistics.load_optimizer.model.Truck;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for validating order compatibility
 */
@Service
public class CompatibilityValidator {

    /**
     * Check if an order can fit in the truck based on weight and volume
     */
    public boolean canFitInTruck(Order order, Truck truck) {
        return order.getWeightLbs() <= truck.getMaxWeightLbs() &&
                order.getVolumeCuft() <= truck.getMaxvolumeCuft();
    }

    /**
     * Check if adding an order would exceed truck capacity
     */
    public boolean wouldExceedCapacity(Order newOrder, int currentWeight, int currentVolume,
                                       Truck truck) {
        return (currentWeight + newOrder.getWeightLbs() > truck.getMaxWeightLbs()) ||
                (currentVolume + newOrder.getVolumeCuft() > truck.getMaxvolumeCuft());
    }

    /**
     * Check if an order has a valid time window
     */
    public boolean hasValidTimeWindow(Order order) {
        return order.hasValidTimeWindow();
    }

    /**
     * Check if a new order is compatible with already selected orders
     */
    public boolean isCompatibleWithSelection(Order newOrder, List<Order> selectedOrders) {
        if (selectedOrders.isEmpty()) {
            return true;
        }

        for (Order existing : selectedOrders) {
            // Check route compatibility
            if (!newOrder.hasSameRoute(existing)) {
                return false;
            }

            // Check hazmat compatibility
            if (!newOrder.isCompaitableWith(existing)) {
                return false;
            }

            // Check time window validity
            if (!newOrder.hasValidTimeWindow() || !existing.hasValidTimeWindow()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Pre-filter orders to only include those that can fit in the truck
     */
    public boolean isValidOrder(Order order, Truck truck) {
        return canFitInTruck(order, truck) && hasValidTimeWindow(order);
    }
}