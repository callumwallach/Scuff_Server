package nz.co.scuff.data.journey.snapshot;

import nz.co.scuff.data.base.snapshot.ModifiableSnapshot;
import nz.co.scuff.data.util.TicketState;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by Callum on 10/05/2015.
 */
@XmlRootElement
public class TicketSnapshot extends ModifiableSnapshot implements Comparable {

    private long ticketId;
    private Timestamp issueDate;
    private TicketState state;

    private long stampId;
    private long journeyId;
    private long childId;

    public TicketSnapshot() {
        super();
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public long getStampId() {
        return stampId;
    }

    public void setStampId(long stampId) {
        this.stampId = stampId;
    }

    public long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(long journeyId) {
        this.journeyId = journeyId;
    }

    public long getChildId() {
        return childId;
    }

    public void setChildId(long childId) {
        this.childId = childId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketSnapshot that = (TicketSnapshot) o;

        return ticketId == that.ticketId;

    }

    @Override
    public int hashCode() {
        return (int) (ticketId ^ (ticketId >>> 32));
    }

    @Override
    public int compareTo(Object another) {
        TicketSnapshot other = (TicketSnapshot) another;
        return this.issueDate.compareTo(other.issueDate);
    }

    @Override
    public String toString() {
        return "TicketSnapshot{" +
                "ticketId=" + ticketId +
                ", issueDate=" + issueDate +
                ", stampId=" + stampId +
                ", state=" + state +
                ", journeyId='" + journeyId + '\'' +
                ", childId=" + childId +
                "} " + super.toString();
    }

}
