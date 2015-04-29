package nz.co.scuff.data.journey;

import nz.co.scuff.data.util.TrackingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Callum on 20/04/2015.
 */
@XmlRootElement
@Entity
public class Waypoint implements Serializable, Comparable {

    //private static final long serialVersionUID = 1L;

    @Transient
    private final Logger l = LoggerFactory.getLogger(Waypoint.class.getName());

    @Id
    @Column(name="WaypointId")
    private String waypointId;
    @NotNull
    @Column(name="Latitude")
    private double latitude;
    @NotNull
    @Column(name="Longitude")
    private double longitude;
    @NotNull
    @Column(name="Speed")
    private float speed;
    @NotNull
    @Column(name="Bearing")
    private float bearing;
    @NotNull
    @Column(name="Distance")
    private float distance;
    @NotNull
    @Column(name="Duration")
    private long duration;
    @NotNull
    @Column(name="Provider")
    private String provider;
    @NotNull
    @Column(name="Accuracy")
    private float accuracy;
    @NotNull
    @Column(name="Altitude")
    private double altitude;
    @NotNull
    @Column(name="State")
    @Enumerated(EnumType.STRING)
    private TrackingState state;
    @NotNull
    @Column(name="Created")
    private Timestamp created;

    public Waypoint() {
    }

    public String getWaypointId() {
        return waypointId;
    }

    public void setWaypointId(String id) {
        this.waypointId = id;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public TrackingState getState() {
        return state;
    }

    public void setState(TrackingState state) {
        this.state = state;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public int compareTo(Object another) {
        Waypoint other = (Waypoint)another;
        return this.created.compareTo(other.created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Waypoint waypoint = (Waypoint) o;

        return waypointId.equals(waypoint.waypointId);

    }

    @Override
    public int hashCode() {
        return waypointId.hashCode();
    }
}
