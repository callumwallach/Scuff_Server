package nz.co.scuff.data.family;

import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.base.ModifiableEntity;
import nz.co.scuff.data.base.Snapshotable;
import nz.co.scuff.data.family.snapshot.ChildSnapshot;
import nz.co.scuff.data.journey.Ticket;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Child extends ModifiableEntity implements Snapshotable, Comparable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ChildId")
    protected long childId;
    @NotNull
    @Embedded
    private ChildData childData;

    @ManyToMany(mappedBy="children", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<Adult> parents;

    // one to many
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="children_tickets",
            joinColumns={@JoinColumn(name="ChildId", referencedColumnName="childId")},
            inverseJoinColumns={@JoinColumn(name="TicketId", referencedColumnName="ticketId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Ticket> tickets;

    public Child() {
        super();
    }

    public Child(ChildData data) {
        super();
        this.childData = data;
    }

    public long getChildId() {
        return childId;
    }

    public void setChildId(long childId) {
        this.childId = childId;
    }

    public ChildData getChildData() {
        return childData;
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    public SortedSet<Adult> getParents() {
        if (parents == null) {
            parents = new TreeSet<>();
        }
        return parents;
    }

    public void setParents(SortedSet<Adult> parents) {
        this.parents = parents;
    }

    public SortedSet<Ticket> getTickets() {
        if (tickets == null) {
            tickets = new TreeSet<>();
        }
        return tickets;
    }

    public void setTickets(SortedSet<Ticket> tickets) {
        this.tickets = tickets;
    }

    public ChildSnapshot toSnapshot() {
        ChildSnapshot snapshot = new ChildSnapshot();
        snapshot.setChildId(childId);
        snapshot.setChildData(childData);
        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Child child = (Child) o;

        return childId == child.childId;

    }

    @Override
    public int hashCode() {
        return (int) (childId ^ (childId >>> 32));
    }

    @Override
    public int compareTo(Object another) {
        Child that = (Child)another;
        return this.childData.compareTo(that.childData);

    }

    @Override
    public String toString() {
        String s = "Child{" +
                "childId=" + childId +
                ", personalData=" + childData;
        s += ", parents=";
        if ((parents == null) || parents.isEmpty()) {
            s += "[empty]";
        } else {
            for (Adult a : parents) {
                s += " ";
                s += a.getCoordinatorId();
            }
        }
        s += ", tickets=";
        if ((tickets == null) || tickets.isEmpty()) {
            s += "[empty]";
        } else {
            for (Ticket t : tickets) {
                s += " ";
                s += t.getTicketId();
            }
        }
        s += "} " + super.toString();
        return s;
    }

}