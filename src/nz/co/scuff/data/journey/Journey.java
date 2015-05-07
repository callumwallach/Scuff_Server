package nz.co.scuff.data.journey;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import nz.co.scuff.data.util.TrackingState;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 20/04/2015.
 */
@Entity
public class Journey implements Serializable, Comparable {

    @Id
    @Column(name="JourneyId")
    private String journeyId;
    @NotNull
    @Column(name="AppId")
    private String appId;
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

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="school")
    private School school;
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="driver")
    private Driver driver;
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="route")
    private Route route;

    // TODO fix sort order to ensure most recent is first

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="JourneyId", referencedColumnName="JourneyId")
    @OrderBy("created DESC")
    @Sort(type = SortType.NATURAL)
    private SortedSet<Waypoint> waypoints;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="journey_passengers",
            joinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")},
            inverseJoinColumns={@JoinColumn(name="PassengerId", referencedColumnName="personId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Passenger> passengers;

    // TODO sync with sorted set to ensure first is the most recent
    public Waypoint getMostRecentWaypoint() {
        //return waypoints.first();
        return waypoints.last();
    }

    public Journey() {}

    public Journey(JourneySnapshot snapshot) {
        this.journeyId = snapshot.getJourneyId();
        this.appId = snapshot.getAppId();
        this.source = snapshot.getSource();
        // school, route and driver set manually
        this.totalDistance = snapshot.getTotalDistance();
        this.totalDuration = snapshot.getTotalDuration();
        this.created = snapshot.getCreated();
        this.completed = snapshot.getCompleted();
        this.state = snapshot.getState();

        waypoints = new TreeSet<>();
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public SortedSet<Waypoint> getWaypoints() {
        if (waypoints == null) {
            waypoints = new TreeSet<>();
        }
        return waypoints;
    }

    public void setWaypoints(SortedSet<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public SortedSet<Passenger> getPassengers() {
        if (passengers == null) {
            passengers = new TreeSet<>();
        }
        return passengers;
    }

    public void setPassengers(SortedSet<Passenger> passengers) {
        this.passengers = passengers;
    }

    public JourneySnapshot toSnapshot() {
        JourneySnapshot snapshot = new JourneySnapshot();
        snapshot.setJourneyId(journeyId);
        snapshot.setAppId(appId);
        snapshot.setSource(source);
        snapshot.setTotalDistance(totalDistance);
        snapshot.setTotalDuration(totalDuration);
        snapshot.setCreated(created);
        snapshot.setCompleted(completed);
        snapshot.setState(state);
        return snapshot;
    }

    @Override
    public int compareTo(Object another) {
        Journey other = (Journey)another;
        return other.created == null ? 1 : this.created.compareTo(other.created);
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

    @Override
    public String toString() {
        return "Journey{" +
                "journeyId='" + journeyId + '\'' +
                ", appId='" + appId + '\'' +
                ", source='" + source + '\'' +
                ", totalDistance=" + totalDistance +
                ", totalDuration=" + totalDuration +
                ", created=" + created +
                ", completed=" + completed +
                ", state=" + state +
                ", school=" + school.getName() +
                ", driver=" + driver.getFirstName() +
                ", route=" + route.getName() +
                '}';
    }
}
