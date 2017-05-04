package com.app.basarnas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by luthfi on 25/04/2017.
 */

public class RequestReport {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("reports")
    @Expose
    private ArrayList<Report> reports = new ArrayList<>();

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
}
