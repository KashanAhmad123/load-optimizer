package com.logistics.load_optimizer.model;

public class Truck {
    private String id;
    private int maxWeightLbs;
    private int maxvolumeCuft;

    public Truck(){}

    public Truck(String id, int maxWeightLbs, int maxvolumeCuft) {
        this.id = id;
        this.maxWeightLbs = maxWeightLbs;
        this.maxvolumeCuft = maxvolumeCuft;
    }

    public int getMaxWeightLbs() {
        return maxWeightLbs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMaxWeightLbs(int maxWeightLbs) {
        this.maxWeightLbs = maxWeightLbs;
    }

    public int getMaxvolumeCuft() {
        return maxvolumeCuft;
    }

    public void setMaxvolumeCuft(int maxvolumeCuft) {
        this.maxvolumeCuft = maxvolumeCuft;
    }

    public boolean canAccommodate(int weightLbs, int volumeCuft){
        return weightLbs<=maxWeightLbs && volumeCuft<=maxvolumeCuft;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id='" + id + '\'' +
                ", maxWeightLbs=" + maxWeightLbs +
                ", maxvolumeCuft=" + maxvolumeCuft +
                '}';
    }
}
