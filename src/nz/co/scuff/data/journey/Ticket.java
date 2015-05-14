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
    @Column(name="IssueDate")
    private Timestamp issueDate;

    @Column(name="Stamp")
    private Stamp stamp;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Journey")
    private Journey journey;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Passenger")
    private Passenger passenger;

    public Ticket() {}

    public Ticket(TicketSnapshot snapshot) {
        this.ticketId = snapshot.getTicketId();
        this.issueDate = snapshot.getIssueDate();
        this.stamp = new Stamp(snapshot.getStamp());
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public TicketSnapshot toSnapshot() {
        TicketSnapshot snapshot = new TicketSnapshot();
        snapshot.setTicketId(this.ticketId);
        snapshot.setIssueDate(this.issueDate);
        snapshot.setStamp(this.stamp.toSnapshot());
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
        return this.issueDate.compareTo(other.issueDate);
    }
}
