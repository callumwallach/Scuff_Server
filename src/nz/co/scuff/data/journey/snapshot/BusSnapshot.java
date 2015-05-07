package nz.co.scuff.data.journey.snapshot;

import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.journey.Waypoint;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Callum on 7/05/2015.
 */
public class BusSnapshot implements Comparable, Serializable {

    private String journeyId;
    private long routeId;
    private long schoolId;

    private String driverName;

    private double latitude;
    private double longitude;
    private float speed;
    private float bearing;
    private float accuracy;
    private double altitude;
    private Timestamp created;

    public BusSnapshot() {
        super();
    }

    public BusSnapshot(Journey journey) {
        this.journeyId = journey.getJourneyId();
        this.routeId = journey.getRoute().getRouteId();
        this.schoolId = journey.getSchool().getSchoolId();
        this.driverName = journey.getDriver().getFirstName();
        Waypoint waypoint = journey.getMostRecentWaypoint();
        this.latitude = waypoint.getLatitude();
        this.longitude = waypoint.getLongitude();
        this.speed = waypoint.getSpeed();
        this.bearing = waypoint.getBearing();
        this.accuracy = waypoint.getAccuracy();
        this.altitude = waypoint.getAltitude();
        this.created = waypoint.getCreated();
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public int compareTo(Object another) {
        BusSnapshot other = (BusSnapshot)another;
        return this.created.compareTo(other.created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BusSnapshot bus = (BusSnapshot) o;

        if (!journeyId.equals(bus.journeyId)) return false;
        return created.equals(bus.created);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + journeyId.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BusSnapshot{");
        sb.append("journeyId='").append(journeyId).append('\'');
        sb.append(", routeId=").append(routeId);
        sb.append(", schoolId=").append(schoolId);
        sb.append(", driverName='").append(driverName).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", speed=").append(speed);
        sb.append(", bearing=").append(bearing);
        sb.append(", accuracy=").append(accuracy);
        sb.append(", altitude=").append(altitude);
        sb.append(", created=").append(created);
        sb.append('}');
        return sb.toString();
    }
}
