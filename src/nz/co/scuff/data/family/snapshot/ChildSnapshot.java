package nz.co.scuff.data.family.snapshot;

import nz.co.scuff.data.base.snapshot.ModifiableSnapshot;
import nz.co.scuff.data.family.ChildData;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class ChildSnapshot extends ModifiableSnapshot {

    private long childId;
    private ChildData childData;
    private Set<Long> parentIds;
    private Set<Long> ticketIds;

    public ChildSnapshot() {
        super();
        parentIds = new HashSet<>();
        ticketIds = new HashSet<>();
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

    public Set<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(Set<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public Set<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(Set<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }

    @Override
    public String toString() {
        return "ChildSnapshot{" +
                "childId=" + childId +
                ", childData=" + childData +
                ", parentIds=" + parentIds +
                ", ticketIds=" + ticketIds +
                '}';
    }
}
