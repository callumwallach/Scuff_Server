package nz.co.scuff.data.school;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.family.Driver;
import nz.co.scuff.data.family.Passenger;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class School implements Comparable, Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SchoolId")
    private long schoolId;
    @NotNull
    @Column(name="Name")
    private String name;
    @Column(name="Latitude")
    private double latitude;
    @Column(name="Longitude")
    private double longitude;
    @Column(name="Altitude")
    private double altitude;

    // one to many
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="school_routes",
            joinColumns={@JoinColumn(name="SchoolId", referencedColumnName="schoolId")},
            inverseJoinColumns={@JoinColumn(name="RouteId", referencedColumnName="routeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Route> routes;

    // many to many
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="school_children",
            joinColumns={@JoinColumn(name="SchoolId", referencedColumnName="schoolId")},
            inverseJoinColumns={@JoinColumn(name="ChildId", referencedColumnName="personId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Passenger> children;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="school_drivers",
            joinColumns={@JoinColumn(name="SchoolId", referencedColumnName="schoolId")},
            inverseJoinColumns={@JoinColumn(name="DriverId", referencedColumnName="personId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Driver> drivers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="school_journeys",
            joinColumns={@JoinColumn(name="SchoolId", referencedColumnName="schoolId")},
            inverseJoinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Journey> journeys;

    public School() {
        super();
    }

    public School(String name, double latitude, double longitude, double altitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
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

    public SortedSet<Route> getRoutes() {
        if (routes == null) {
            routes = new TreeSet<>();
        }
        return routes;
    }

    public void setRoutes(SortedSet<Route> routes) {
        this.routes = routes;
    }

    public SortedSet<Passenger> getChildren() {
        if (children == null) {
            children = new TreeSet<>();
        }
        return children;
    }

    public void setChildren(SortedSet<Passenger> children) {
        this.children = children;
    }

    public SortedSet<Driver> getDrivers() {
        if (drivers == null) {
            drivers = new TreeSet<>();
        }
        return drivers;
    }

    public void setDrivers(SortedSet<Driver> drivers) {
        this.drivers = drivers;
    }

    public SortedSet<Journey> getJourneys() {
        if (journeys == null) {
            journeys = new TreeSet<>();
        }
        return journeys;
    }

    public void setJourneys(SortedSet<Journey> journeys) {
        this.journeys = journeys;
    }

    public SchoolSnapshot toSnapshot() {
        SchoolSnapshot snapshot = new SchoolSnapshot();
        snapshot.setSchoolId(schoolId);
        snapshot.setName(name);
        snapshot.setLatitude(latitude);
        snapshot.setLongitude(longitude);
        snapshot.setAltitude(altitude);
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        School school = (School) o;

        return schoolId == school.schoolId;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (schoolId ^ (schoolId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        School other = (School)another;
        return other.name == null ? 1 : this.name.compareTo(other.name);
    }
}