package nz.co.scuff.data.journey;

import java.io.Serializable;
import java.sql.Timestamp;

import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;

import javax.persistence.*;

/**
 * Created by Callum on 10/05/2015.
 */
@Entity
public class Ticket implements Comparable, Serializable {

    @Id
    @Column(name="TicketId")
    private String ticketId;
    @Column(name="Latitude")
    private double latitude;
    @Column(name="Longitude")
    private long longitude;
    @Column(name="Timestamp")
    private Timestamp timestamp;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Journey")
    private Journey journey;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Passenger")
    private Passenger passenger;

    public Ticket() {}

    public Ticket(TicketSnapshot snapshot) {
        this.ticketId = snapshot.getTicketId();
        this.latitude = snapshot.getLatitude();
        this.longitude = snapshot.getLongitude();
        this.timestamp = snapshot.getTimestamp();
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String boardingInfoId) {
        this.ticketId = boardingInfoId;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
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

    public TicketSnapshot toSnapshot() {
        TicketSnapshot snapshot = new TicketSnapshot();
        snapshot.setTicketId(this.ticketId);
        snapshot.setLatitude(this.latitude);
        snapshot.setLongitude(this.longitude);
        snapshot.setTimestamp(this.timestamp);
        snapshot.setJourneyId(this.journey.getJourneyId());
        snapshot.setPassengerId(this.passenger.getPersonId());
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ticket that = (Ticket) o;

        return !(ticketId != null ? !ticketId.equals(that.ticketId) : that.ticketId != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ticketId != null ? ticketId.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object another) {
        Ticket other = (Ticket) another;
        return this.timestamp.compareTo(other.timestamp);
    }
}
