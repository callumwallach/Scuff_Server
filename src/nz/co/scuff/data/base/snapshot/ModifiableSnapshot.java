package nz.co.scuff.data.base.snapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Callum on 3/06/2015.
 */
@XmlRootElement
public class ModifiableSnapshot implements Snapshot, Serializable {

    protected boolean active;
    protected Timestamp lastModified;

    public ModifiableSnapshot() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isDirty(Timestamp timestamp) {
        return lastModified.after(timestamp);
    }

    @Override
    public String toString() {
        return "ModifiableSnapshot{" +
                "active=" + active +
                ", lastModified=" + lastModified +
                '}';
    }

}
