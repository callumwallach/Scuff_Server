package nz.co.scuff.data.family;

import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.family.snapshot.DriverSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Driver extends Person {

/*    @NotNull
    private Schedule schedule;*/

    @NotNull
    @Column(name="Email")
    private String email;
    @NotNull
    @Column(name="Phone")
    private String phone;

    @ManyToMany(mappedBy="drivers", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<School> schoolsDrivingFor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="parents_children",
            joinColumns={@JoinColumn(name="ParentId", referencedColumnName="personId")},
            inverseJoinColumns={@JoinColumn(name="ChildId", referencedColumnName="personId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Passenger> children;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="driver_routes",
            joinColumns={@JoinColumn(name="DriverId", referencedColumnName="personId")},
            inverseJoinColumns={@JoinColumn(name="RouteId", referencedColumnName="routeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Route> routesDriven;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="driver_journeys",
            joinColumns={@JoinColumn(name="DriverId", referencedColumnName="personId")},
            inverseJoinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Journey> journeys;

    public Driver() { }

    public Driver(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }

    public Driver(String firstName, String lastName, Gender gender, String picture) {
        super(firstName, lastName, gender, picture);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SortedSet<School> getSchoolsDrivingFor() {
        if (schoolsDrivingFor == null) {
            schoolsDrivingFor = new TreeSet<>();
        }return schoolsDrivingFor;
    }

    public void setSchoolsDrivingFor(SortedSet<School> schoolsDrivingFor) {
        this.schoolsDrivingFor = schoolsDrivingFor;
    }

    public SortedSet<Passenger> getChildren() {
        if (children == null) {
            children = new TreeSet<>();
        }return children;
    }

    public void setChildren(SortedSet<Passenger> children) {
        this.children = children;
    }

    public SortedSet<Route> getRoutesDriven() {
        if (routesDriven == null) {
            routesDriven = new TreeSet<>();
        }return routesDriven;
    }

    public void setRoutesDriven(SortedSet<Route> routesDriven) {
        this.routesDriven = routesDriven;
    }

    public SortedSet<Journey> getJourneys() {
        if (journeys == null) {
            journeys = new TreeSet<>();
        }return journeys;
    }

    public void setJourneys(SortedSet<Journey> journeys) {
        this.journeys = journeys;
    }

    public DriverSnapshot toSnapshot() {
        DriverSnapshot snapshot = new DriverSnapshot();
        snapshot.setPersonId(personId);
        snapshot.setFirstName(firstName);
        snapshot.setMiddleName(middleName);
        snapshot.setLastName(lastName);
        snapshot.setGender(gender);
        snapshot.setPicture(picture);
        snapshot.setEmail(email);
        snapshot.setPhone(phone);
        return snapshot;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}