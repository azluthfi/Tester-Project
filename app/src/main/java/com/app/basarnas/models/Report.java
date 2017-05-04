package com.app.basarnas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by luthfi on 25/04/2017.
 */

public class Report {
    @SerializedName("report_id")
    @Expose
    private String reportId;
    @SerializedName("report_date")
    @Expose
    private String reportDate;
    @SerializedName("report_desc")
    @Expose
    private String reportDesc;
    @SerializedName("report_status")
    @Expose
    private String reportStatus;
    @SerializedName("report_location")
    @Expose
    private String reportLocation;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }

}
