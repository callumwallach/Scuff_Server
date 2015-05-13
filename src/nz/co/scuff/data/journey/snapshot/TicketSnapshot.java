package nz.co.scuff.data.journey.snapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by Callum on 10/05/2015.
 */
@XmlRootElement
public class TicketSnapshot implements Comparable {

    private String ticketId;
    private double latitude;
    private long longitude;
    private Timestamp timestamp;
    private String journeyId;
    private long passengerId;

    public TicketSnapshot() {}

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketSnapshot that = (TicketSnapshot) o;

        return !(ticketId != null ? !ticketId.equals(that.ticketId) : that.ticketId != null);

    }

    @Override
    public int hashCode() {
        return ticketId != null ? ticketId.hashCode() : 0;
    }

    @Override
    public int compareTo(Object another) {
        TicketSnapshot other = (TicketSnapshot) another;
        return this.timestamp.compareTo(other.timestamp);
    }
}
