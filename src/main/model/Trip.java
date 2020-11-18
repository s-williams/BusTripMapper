package main.model;

import java.util.Date;

/**
 * Bean containing all data relevant to a record of a trip on a bus.
 */
public class Trip {
    private Date started;
    private Date finished;
    private String fromStopId;
    private String toStopId;
    private int chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private String status;

    public Trip() {
    }

    public String toCsv() {
        return started + ", " +
                finished + ", " +
                fromStopId + ", " +
                toStopId + ", " +
                chargeAmount + ", " +
                companyId + ", " +
                busId + ", " +
                pan + ", " +
                status;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "started=" + started +
                ", finished=" + finished +
                ", fromStopId='" + fromStopId + '\'' +
                ", toStopId='" + toStopId + '\'' +
                ", chargeAmount=" + chargeAmount +
                ", companyId='" + companyId + '\'' +
                ", busId='" + busId + '\'' +
                ", pan='" + pan + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
