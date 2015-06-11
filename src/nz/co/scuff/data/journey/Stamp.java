package nz.co.scuff.data.journey;

import java.io.Serializable;
import java.sql.Timestamp;

import nz.co.scuff.data.base.Snapshotable;
import nz.co.scuff.data.journey.snapshot.StampSnapshot;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Callum on 14/05/2015.
 */
@Embeddable
public class Stamp implements Snapshotable, Serializable {

    @Column(name="StampId")
    private long stampId;
    @Column(name="Latitude")
    private double latitude;
    @Column(name="Longitude")
    private double longitude;
    @Column(name="StampDate")
    private Timestamp stampDate;

    public Stamp() {}

    public Stamp(StampSnapshot snapshot) {
        this.stampId = snapshot.getStampId();
        this.latitude = snapshot.getLatitude();
        this.longitude = snapshot.getLongitude();
        this.stampDate = snapshot.getStampDate();
    }

    public long getStampId() {
        return stampId;
    }

    public void setStampId(long stampId) {
        this.stampId = stampId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getStampDate() {
        return stampDate;
    }

    public void setStampDate(Timestamp stampDate) {
        this.stampDate = stampDate;
    }

    public StampSnapshot toSnapshot() {
        StampSnapshot snapshot = new StampSnapshot();
        snapshot.setStampId(this.stampId);
        snapshot.setLatitude(this.latitude);
        snapshot.setLongitude(this.longitude);
        snapshot.setStampDate(this.stampDate);
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stamp stamp = (Stamp) o;

        return stampId == stamp.stampId;

    }

    @Override
    public int hashCode() {
        return (int) (stampId ^ (stampId >>> 32));
    }

    @Override
    public String toString() {
        return "Stamp{" +
                "stampId=" + stampId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stampDate=" + stampDate +
                '}';
    }
}
