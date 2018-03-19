
package com.corporateapp.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmergencyContact {

    @SerializedName("polis_station")
    @Expose
    private List<PolisStation> polisStation = null;
    @SerializedName("ambulance")
    @Expose
    private List<Ambulance> ambulance = null;
    @SerializedName("hospitals")
    @Expose
    private List<Hospital> hospitals = null;
    @SerializedName("blood_bank")
    @Expose
    private List<BloodBank> bloodBank = null;
    @SerializedName("fire_brigade")
    @Expose
    private List<FireBrigade> fireBrigade = null;
    @SerializedName("water_supply")
    @Expose
    private List<Object> waterSupply = null;

    public List<PolisStation> getPolisStation() {
        return polisStation;
    }

    public void setPolisStation(List<PolisStation> polisStation) {
        this.polisStation = polisStation;
    }

    public List<Ambulance> getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(List<Ambulance> ambulance) {
        this.ambulance = ambulance;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<BloodBank> getBloodBank() {
        return bloodBank;
    }

    public void setBloodBank(List<BloodBank> bloodBank) {
        this.bloodBank = bloodBank;
    }

    public List<FireBrigade> getFireBrigade() {
        return fireBrigade;
    }

    public void setFireBrigade(List<FireBrigade> fireBrigade) {
        this.fireBrigade = fireBrigade;
    }

    public List<Object> getWaterSupply() {
        return waterSupply;
    }

    public void setWaterSupply(List<Object> waterSupply) {
        this.waterSupply = waterSupply;
    }

}
