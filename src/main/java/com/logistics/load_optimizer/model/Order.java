package com.logistics.load_optimizer.model;

import java.time.LocalDate;

public class Order {
    private String id;
    private long payoutCents;
    private int weightLbs;
    private int volumeCuft;
    private String origin;
    private String destination;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private boolean isHazmat;

    public Order(String id, long payoutCents, int weightLbs, int volumeCuft, String origin, String destination, LocalDate pickupDate, LocalDate deliveryDate, boolean isHazmat) {
        this.id = id;
        this.payoutCents = payoutCents;
        this.weightLbs = weightLbs;
        this.volumeCuft = volumeCuft;
        this.origin = origin;
        this.destination = destination;
        this.pickupDate = pickupDate;
        this.deliveryDate = deliveryDate;
        this.isHazmat = isHazmat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPayoutCents() {
        return payoutCents;
    }

    public void setPayoutCents(long payoutCents) {
        this.payoutCents = payoutCents;
    }

    public int getWeightLbs() {
        return weightLbs;
    }

    public void setWeightLbs(int weightLbs) {
        this.weightLbs = weightLbs;
    }

    public int getVolumeCuft() {
        return volumeCuft;
    }

    public void setVolumeCuft(int volumeCuft) {
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

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isHazmat() {
        return isHazmat;
    }

    public void setHazmat(boolean hazmat) {
        isHazmat = hazmat;
    }

    public boolean hasSameRoute(Order other){
        return this.origin.equals(other.origin) &&
                this.destination.equals(other.destination);
    }

    public boolean isCompaitableWith(Order other){
        return this.isHazmat== other.isHazmat;
    }

    public boolean hasValidTimeWindow() {
        return pickupDate != null && deliveryDate != null &&
                !pickupDate.isAfter(deliveryDate);
    }

    public double efficiencyRatio(){
        int maxResource= Math.max(weightLbs,volumeCuft);
        return maxResource>0?(double) payoutCents/maxResource:0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", payoutCents=" + payoutCents +
                ", weightLbs=" + weightLbs +
                ", volumeCuft=" + volumeCuft +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupDate=" + pickupDate +
                ", deliveryDate=" + deliveryDate +
                ", isHazmat=" + isHazmat +
                '}';
    }
}
