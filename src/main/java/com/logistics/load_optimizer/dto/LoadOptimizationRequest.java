package com.logistics.load_optimizer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public class LoadOptimizationRequest {
    @NotNull(message = "Truck information is required")
    @Valid
    private TruckDto truck;

    @NotNull(message = "Orders list is required")
    @Size(min = 0, max = 50, message = "Orders list must contain between 0 and 50 items")
    @Valid
    private List<OrderDto> orders;

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
        this.truck = truck;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }

    /**
     * Nested DTO for Truck
     */
    public static class TruckDto {
        @NotNull(message = "Truck ID is required")
        @Size(min = 1, max = 100, message = "Truck ID must be between 1 and 100 characters")
        private String id;

        @JsonProperty("max_weight_lbs")
        @NotNull(message = "Max weight is required")
        @Positive(message = "Max weight must be positive")
        private Integer maxWeightLbs;

        @JsonProperty("max_volume_cuft")
        @NotNull(message = "Max volume is required")
        @Positive(message = "Max volume must be positive")
        private Integer maxVolumeCuft;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getMaxWeightLbs() {
            return maxWeightLbs;
        }

        public void setMaxWeightLbs(Integer maxWeightLbs) {
            this.maxWeightLbs = maxWeightLbs;
        }

        public Integer getMaxVolumeCuft() {
            return maxVolumeCuft;
        }

        public void setMaxVolumeCuft(Integer maxVolumeCuft) {
            this.maxVolumeCuft = maxVolumeCuft;
        }
    }

    /**
     * Nested DTO for Order
     */
    public static class OrderDto {
        @NotNull(message = "Order ID is required")
        @Size(min = 1, max = 100, message = "Order ID must be between 1 and 100 characters")
        private String id;

        @JsonProperty("payout_cents")
        @NotNull(message = "Payout is required")
        @Positive(message = "Payout must be positive")
        private Long payoutCents;

        @JsonProperty("weight_lbs")
        @NotNull(message = "Weight is required")
        @Positive(message = "Weight must be positive")
        private Integer weightLbs;

        @JsonProperty("volume_cuft")
        @NotNull(message = "Volume is required")
        @Positive(message = "Volume must be positive")
        private Integer volumeCuft;

        @NotNull(message = "Origin is required")
        @Size(min = 1, max = 200, message = "Origin must be between 1 and 200 characters")
        private String origin;

        @NotNull(message = "Destination is required")
        @Size(min = 1, max = 200, message = "Destination must be between 1 and 200 characters")
        private String destination;

        @JsonProperty("pickup_date")
        @NotNull(message = "Pickup date is required")
        private String pickupDate;

        @JsonProperty("delivery_date")
        @NotNull(message = "Delivery date is required")
        private String deliveryDate;

        @JsonProperty("is_hazmat")
        @NotNull(message = "Hazmat flag is required")
        private Boolean isHazmat;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getPayoutCents() {
            return payoutCents;
        }

        public void setPayoutCents(Long payoutCents) {
            this.payoutCents = payoutCents;
        }

        public Integer getWeightLbs() {
            return weightLbs;
        }

        public void setWeightLbs(Integer weightLbs) {
            this.weightLbs = weightLbs;
        }

        public Integer getVolumeCuft() {
            return volumeCuft;
        }

        public void setVolumeCuft(Integer volumeCuft) {
            this.volumeCuft = volumeCuft;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getPickupDate() {
            return pickupDate;
        }

        public void setPickupDate(String pickupDate) {
            this.pickupDate = pickupDate;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Boolean getIsHazmat() {
            return isHazmat;
        }

        public void setIsHazmat(Boolean isHazmat) {
            this.isHazmat = isHazmat;
        }
    }
}
