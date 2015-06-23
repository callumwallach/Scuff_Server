package nz.co.scuff.data.journey;

import java.sql.Timestamp;

import nz.co.scuff.data.base.ModifiableEntity;
import nz.co.scuff.data.base.Snapshotable;
import nz.co.scuff.data.family.Child;
import nz.co.scuff.data.journey.snapshot.TicketSnapshot;
import nz.co.scuff.data.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

/**
 * Created by Callum on 10/05/2015.
 */
@Entity
public class Ticket extends ModifiableEntity implements Snapshotable, Comparable {

    public static final Logger l = LoggerFactory.getLogger(Ticket.class.getCanonicalName());

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
    @JoinColumn(name="Child")
    private Child child;

    public Ticket() {
        super();
    }

    public Ticket(TicketSnapshot snapshot) {
        super();
        this.ticketId = snapshot.getTicketId();
        this.issueDate = snapshot.getIssueDate();
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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
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
        snapshot.setStampId(this.stamp == null ? Constants.LONG_VALUE_SET_TO_NULL : this.stamp.getStampId());
        snapshot.setJourneyId(this.journey.getJourneyId());
        snapshot.setChildId(this.child.getChildId());

        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);

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
        if (another == null) throw new NullPointerException("Cannot compare a ticket with a null value");
        Ticket other = (Ticket) another;
        if (this.equals(other)) return 0;
/*        if (other.issueDate == null) return 1;
        if (this.issueDate == null) return -1;
        return this.issueDate.compareTo(other.issueDate);*/
        return (this.ticketId < other.ticketId) ? -1 : 1;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", issueDate=" + issueDate +
                ", stamp=" + stamp +
                ", journey=" + journey.getJourneyId() +
                ", child=" + child.getChildId() +
                '}';
    }
}
