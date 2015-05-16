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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="TicketId")
    private long ticketId;
    @Column(name="IssueDate")
    private Timestamp issueDate;

    @Column(name="Stamp")
    private Stamp stamp;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Journey")
    private Journey journey;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Passenger")
    private Passenger passenger;

    public Ticket() {}

    public Ticket(TicketSnapshot snapshot) {
        this.ticketId = snapshot.getTicketId();
        this.issueDate = snapshot.getIssueDate();
        this.stamp = (snapshot.getStamp() == null ? null : new Stamp(snapshot.getStamp()));
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
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
        snapshot.setStamp(this.stamp == null ? null : this.stamp.toSnapshot());
        snapshot.setJourneyId(this.journey.getJourneyId());
        snapshot.setPassengerId(this.passenger.getPersonId());
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return ticketId == ticket.ticketId;

    }

    @Override
    public int hashCode() {
        return (int) (ticketId ^ (ticketId >>> 32));
    }

    @Override
    public int compareTo(Object another) {
        Ticket other = (Ticket) another;
        if (other.issueDate == null) return 1;
        if (this.issueDate == null) return -1;
        return this.issueDate.compareTo(other.issueDate);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", issueDate=" + issueDate +
                ", stamp=" + stamp +
                ", journey=" + (journey == null ? "null" : journey.getJourneyId()) +
                ", passenger=" + (passenger == null ? "null" : passenger.getPersonId()) +
                '}';
    }
}
