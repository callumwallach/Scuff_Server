package nz.co.scuff.data.place;

import nz.co.scuff.data.base.ModifiableEntity;
import nz.co.scuff.data.base.Snapshotable;
import nz.co.scuff.data.place.snapshot.PlaceSnapshot;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 3/06/2015.
 */
@Entity
public class Place extends ModifiableEntity implements Snapshotable, Comparable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="PlaceId")
    private long placeId;
    @NotNull
    @Column(name="Name")
    private String name;
    @NotNull
    @Column(name="Latitude")
    private double latitude;
    @NotNull
    @Column(name="Longitude")
    private double longitude;
    @NotNull
    @Column(name="Altitude")
    private double altitude;

    public Place() {
    }

    public Place(String name, double latitude, double longitude, double altitude) {
        super();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public PlaceSnapshot toSnapshot() {
        PlaceSnapshot snapshot = new PlaceSnapshot();
        snapshot.setPlaceId(placeId);
        snapshot.setName(name);
        snapshot.setLatitude(latitude);
        snapshot.setLongitude(longitude);
        snapshot.setAltitude(altitude);
        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        return placeId == place.placeId;

    }

    @Override
    public int hashCode() {
        return (int) (placeId ^ (placeId >>> 32));
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", name=" + name +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        Place other = (Place)another;
        if (this.equals(other)) return 0;
        return other.name == null ? 1 : this.name.compareTo(other.name);

    }
}
