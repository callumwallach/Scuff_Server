package nz.co.scuff.data.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Callum on 3/06/2015.
 */
@MappedSuperclass
public abstract class ModifiableEntity implements Serializable {

    @Column(name="Active")
    protected boolean active;
    @NotNull
    @Column(name="LastModified")
    protected Timestamp lastModified;

    public ModifiableEntity() {
        this.active = true;
        this.lastModified = new Timestamp(System.currentTimeMillis());
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
        return "ModifiableEntity{" +
                "active=" + active +
                ", lastModified=" + lastModified +
                '}';
    }
}
