package com.example.newsten;

import com.google.gson.annotations.SerializedName;

public class CoronaInfo {

    @SerializedName("Country")
    String country ;
    @SerializedName("Confirmed")
    String confirmed;
    @SerializedName("Deaths")
    String death;
    @SerializedName("Recovered")
    String recovered;
    @SerializedName("Active")
    String active ;

    public CoronaInfo(String country, String confirmed, String death, String recovered, String active) {

        this.country = country;

        this.confirmed = confirmed;
        this.death = death;
        this.recovered = recovered;
        this.active = active;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
