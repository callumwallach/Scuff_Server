package nz.co.scuff.data.family;

import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.family.snapshot.PassengerSnapshot;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Passenger extends Person {

    // many to many
    @ManyToMany(mappedBy="children", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<School> schools;

    @ManyToMany(mappedBy="children", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<Driver> parents;

    @ManyToMany(mappedBy="passengers", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<Journey> journeys;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="passenger_routes",
            joinColumns={@JoinColumn(name="PassengerId", referencedColumnName="personId")},
            inverseJoinColumns={@JoinColumn(name="RouteId", referencedColumnName="routeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Route> registeredRoutes;

    public Passenger() { }

    public Passenger(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }

    public Passenger(String firstName, String lastName, Gender gender, String picture) {
        super(firstName, lastName, gender, picture);
    }

    public SortedSet<School> getSchools() {
        if (schools == null) {
            schools = new TreeSet<>();
        }
        return schools;
    }

    public void setSchools(SortedSet<School> schools) {
        this.schools = schools;
    }

    public SortedSet<Driver> getParents() {
        if (parents == null) {
            parents = new TreeSet<>();
        }
        return parents;
    }

    public void setParents(SortedSet<Driver> parents) {
        this.parents = parents;
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

    public SortedSet<Route> getRegisteredRoutes() {
        if (registeredRoutes == null) {
            registeredRoutes = new TreeSet<>();
        }
        return registeredRoutes;
    }

    public void setRegisteredRoutes(SortedSet<Route> registeredRoutes) {
        this.registeredRoutes = registeredRoutes;
    }

    public PassengerSnapshot toSnapshot() {
        PassengerSnapshot snapshot = new PassengerSnapshot();
        snapshot.setPersonId(personId);
        snapshot.setFirstName(firstName);
        snapshot.setMiddleName(middleName);
        snapshot.setLastName(lastName);
        snapshot.setGender(gender);
        snapshot.setPicture(picture);
        return snapshot;
    }

    @Override
    public String toString() {
        return "Passenger{} " + super.toString();
    }

}