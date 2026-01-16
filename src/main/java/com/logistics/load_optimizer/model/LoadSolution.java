package com.logistics.load_optimizer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadSolution {
    private String truckId;
    private List<Order> selectedOrders;
    private long totalPayoutCents;
    private int totalWeightLbs;
    private int totalVolumeCuft;
    private int maxWeightLbs;
    private int maxVolumeCuft;

    public LoadSolution(){
        this.selectedOrders = new ArrayList<>();
        this.totalPayoutCents = 0L;
        this.totalWeightLbs = 0;
        this.totalVolumeCuft = 0;
    }

    public LoadSolution(String truckId, int maxWeightLbs, int maxVolumeCuft) {
        this.truckId = truckId;
        this.maxWeightLbs = maxWeightLbs;
        this.maxVolumeCuft = maxVolumeCuft;
        this.selectedOrders = new ArrayList<>();
        this.totalPayoutCents = 0L;
        this.totalWeightLbs = 0;
        this.totalVolumeCuft = 0;
    }

    public void addOrder(Order order){
        selectedOrders.add(order);
        totalPayoutCents+=order.getPayoutCents();
        totalVolumeCuft+=order.getVolumeCuft();
        totalWeightLbs+=order.getWeightLbs();
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public List<Order> getSelectedOrders() {
        return Collections.unmodifiableList(selectedOrders);
    }

    public void setSelectedOrders(List<Order> selectedOrders) {
        this.selectedOrders = new ArrayList<>(selectedOrders);
        recalculateTotals();
    }

    public long getTotalPayoutCents() {
        return totalPayoutCents;
    }

    public int getTotalWeightLbs() {
        return totalWeightLbs;
    }

    public int getTotalVolumeCuft() {
        return totalVolumeCuft;
    }

    public int getMaxWeightLbs() {
        return maxWeightLbs;
    }

    public void setMaxWeightLbs(int maxWeightLbs) {
        this.maxWeightLbs = maxWeightLbs;
    }

    public int getMaxVolumeCuft() {
        return maxVolumeCuft;
    }

    public void setMaxVolumeCuft(int maxVolumeCuft) {
        this.maxVolumeCuft = maxVolumeCuft;
    }

    public double getWeightUtilizationPercent() {
        return maxWeightLbs > 0 ? (totalWeightLbs * 100.0) / maxWeightLbs : 0.0;
    }

    public double getVolumeUtilizationPercent() {
        return maxVolumeCuft > 0 ? (totalVolumeCuft * 100.0) / maxVolumeCuft : 0.0;
    }

    public boolean isEmpty() {
        return selectedOrders.isEmpty();
    }

    public int getOrderCount() {
        return selectedOrders.size();
    }

    public boolean isBetterThan(LoadSolution other) {
        if (other == null) return true;
        return this.totalPayoutCents > other.totalPayoutCents;
    }

    @Override
    public String toString() {
        return "LoadSolution{" +
                "truckId='" + truckId + '\'' +
                ", orderCount=" + selectedOrders.size() +
                ", selectedOrders=" + selectedOrders +
                ", totalPayoutCents=" + totalPayoutCents +
                ", totalWeightLbs=" + totalWeightLbs +
                ", totalVolumeCuft=" + totalVolumeCuft +
                ", weightUtilization=" + String.format("%.2f%%", getWeightUtilizationPercent()) +
                ", volumeUtilization=" + String.format("%.2f%%", getVolumeUtilizationPercent()) +
                '}';
    }

    private void recalculateTotals() {
        totalPayoutCents = 0L;
        totalWeightLbs = 0;
        totalVolumeCuft = 0;
        for (Order order : selectedOrders) {
            totalPayoutCents += order.getPayoutCents();
            totalWeightLbs += order.getWeightLbs();
            totalVolumeCuft += order.getVolumeCuft();
        }
    }

}
