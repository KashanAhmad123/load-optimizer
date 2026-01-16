package com.logistics.load_optimizer.mapper;

import com.logistics.load_optimizer.dto.LoadOptimizationRequest;
import com.logistics.load_optimizer.dto.LoadOptimizationResponse;
import com.logistics.load_optimizer.model.LoadSolution;
import com.logistics.load_optimizer.model.Order;
import com.logistics.load_optimizer.model.Truck;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {
    /**
     * Convert TruckDto to Truck model
     */
    public Truck toTruckModel(LoadOptimizationRequest.TruckDto dto) {
        if (dto == null) {
            return null;
        }
        return new Truck(
                dto.getId(),
                dto.getMaxWeightLbs(),
                dto.getMaxVolumeCuft()
        );
    }

    /**
     * Convert OrderDto to Order model
     */
    public Order toOrderModel(LoadOptimizationRequest.OrderDto dto) {
        if (dto == null) {
            return null;
        }

        LocalDate pickupDate = parseDate(dto.getPickupDate());
        LocalDate deliveryDate = parseDate(dto.getDeliveryDate());

        return new Order(
                dto.getId(),
                dto.getPayoutCents(),
                dto.getWeightLbs(),
                dto.getVolumeCuft(),
                dto.getOrigin(),
                dto.getDestination(),
                pickupDate,
                deliveryDate,
                dto.getIsHazmat()
        );
    }

    /**
     * Convert list of OrderDto to list of Order models
     */
    public List<Order> toOrderModels(List<LoadOptimizationRequest.OrderDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream()
                .map(this::toOrderModel)
                .collect(Collectors.toList());
    }

    /**
     * Convert LoadSolution model to LoadOptimizationResponse DTO
     */
    public LoadOptimizationResponse toResponseDto(LoadSolution solution) {
        if (solution == null) {
            return null;
        }

        List<String> orderIds = solution.getSelectedOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        return new LoadOptimizationResponse(
                solution.getTruckId(),
                orderIds,
                solution.getTotalPayoutCents(),
                solution.getTotalWeightLbs(),
                solution.getTotalVolumeCuft(),
                roundToTwoDecimals(solution.getWeightUtilizationPercent()),
                roundToTwoDecimals(solution.getVolumeUtilizationPercent())
        );
    }

    /**
     * Parse date string to LocalDate
     */
    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr + ". Expected format: YYYY-MM-DD");
        }
    }

    /**
     * Round double to 2 decimal places
     */
    private Double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
