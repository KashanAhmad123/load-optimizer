package com.logistics.load_optimizer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data Transfer Object for Load Optimization Response
 */
public class LoadOptimizationResponse {

    @JsonProperty("truck_id")
    private String truckId;

    @JsonProperty("selected_order_ids")
    private List<String> selectedOrderIds;

    @JsonProperty("total_payout_cents")
    private Long totalPayoutCents;

    @JsonProperty("total_weight_lbs")
    private Integer totalWeightLbs;

    @JsonProperty("total_volume_cuft")
    private Integer totalVolumeCuft;

    @JsonProperty("utilization_weight_percent")
    private Double utilizationWeightPercent;

    @JsonProperty("utilization_volume_percent")
    private Double utilizationVolumePercent;

    public LoadOptimizationResponse() {
    }

    public LoadOptimizationResponse(String truckId, List<String> selectedOrderIds,
                                    Long totalPayoutCents, Integer totalWeightLbs,
                                    Integer totalVolumeCuft, Double utilizationWeightPercent,
                                    Double utilizationVolumePercent) {
        this.truckId = truckId;
        this.selectedOrderIds = selectedOrderIds;
        this.totalPayoutCents = totalPayoutCents;
        this.totalWeightLbs = totalWeightLbs;
        this.totalVolumeCuft = totalVolumeCuft;
        this.utilizationWeightPercent = utilizationWeightPercent;
        this.utilizationVolumePercent = utilizationVolumePercent;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public List<String> getSelectedOrderIds() {
        return selectedOrderIds;
    }

    public void setSelectedOrderIds(List<String> selectedOrderIds) {
        this.selectedOrderIds = selectedOrderIds;
    }

    public Long getTotalPayoutCents() {
        return totalPayoutCents;
    }

    public void setTotalPayoutCents(Long totalPayoutCents) {
        this.totalPayoutCents = totalPayoutCents;
    }

    public Integer getTotalWeightLbs() {
        return totalWeightLbs;
    }

    public void setTotalWeightLbs(Integer totalWeightLbs) {
        this.totalWeightLbs = totalWeightLbs;
    }

    public Integer getTotalVolumeCuft() {
        return totalVolumeCuft;
    }

    public void setTotalVolumeCuft(Integer totalVolumeCuft) {
        this.totalVolumeCuft = totalVolumeCuft;
    }

    public Double getUtilizationWeightPercent() {
        return utilizationWeightPercent;
    }

    public void setUtilizationWeightPercent(Double utilizationWeightPercent) {
        this.utilizationWeightPercent = utilizationWeightPercent;
    }

    public Double getUtilizationVolumePercent() {
        return utilizationVolumePercent;
    }

    public void setUtilizationVolumePercent(Double utilizationVolumePercent) {
        this.utilizationVolumePercent = utilizationVolumePercent;
    }
}
