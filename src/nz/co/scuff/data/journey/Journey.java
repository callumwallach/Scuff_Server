package nz.co.scuff.data.journey;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 20/04/2015.
 */
@XmlRootElement
@Entity
public class Journey implements Serializable, Comparable {

    //private static final long serialVersionUID = 2L;

    public enum TrackingState {
        RECORDING, PAUSED, COMPLETED
    }
/*

    @XmlEnum
    public enum SortingType {
        @XmlEnumValue("recording")
        RECORDING,
        @XmlEnumValue("paused")
        PAUSED;

        private static Map<String, SortingType> sortingTypeByValue = new HashMap<>();
        private static Map<SortingType, String> valueBySortingType = new HashMap<>();
        static {
            SortingType[] enumConstants = SortingType.class.getEnumConstants();
            for (SortingType sortingType : enumConstants) {
                try {
                    String value = SortingType.class.getField(sortingType.name()).getAnnotation(XmlEnumValue.class).value();
                    sortingTypeByValue.put(value, sortingType);
                    valueBySortingType.put(sortingType, value);
                } catch (NoSuchFieldException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        @JsonCreator
        public static SortingType create(String value) {
            return sortingTypeByValue.get(value);
        }

        @JsonValue
        public String getValue() {
            return valueBySortingType.get(this);
        }
    }
*/

    @Id
    @Column(name="JourneyId")
    private String journeyId;
    @NotNull
    @Column(name="AppId")
    private long appId;
    @NotNull
    @Column(name="SchoolId")
    private String schoolId;
    @NotNull
    @Column(name="DriverId")
    private String driverId;
    @NotNull
    @Column(name="RouteId")
    private String routeId;
    @NotNull
    @Column(name="Source")
    private String source;
    @NotNull
    @Column(name="TotalDistance")
    private float totalDistance;
    @NotNull
    @Column(name="TotalDuration")
    private long totalDuration;
    @NotNull
    @Column(name="Created")
    private Timestamp created;
    // null until completed
    @Column(name="Completed")
    private Timestamp completed;
    @NotNull
    @Column(name="State")
    @Enumerated(EnumType.STRING)
    private TrackingState state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="JourneyId", referencedColumnName="JourneyId")
    @OrderBy("created DESC")
    @Sort(type = SortType.NATURAL)
    private SortedSet<Waypoint> waypoints;

    public Journey() {
        waypoints = new TreeSet<>();
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String id) {
        this.journeyId = id;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getCompleted() {
        return completed;
    }

    public void setCompleted(Timestamp completed) {
        this.completed = completed;
    }

    public TrackingState getState() {
        return state;
    }

    public void setState(TrackingState state) {
        this.state = state;
    }

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public SortedSet<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(SortedSet<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    @Override
    public int compareTo(Object another) {
        Journey other = (Journey)another;
        return this.created.compareTo(other.created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (!journeyId.equals(journey.journeyId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return journeyId.hashCode();
    }
}
