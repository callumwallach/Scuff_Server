package nz.co.scuff.data.base;

import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.institution.Route;
import nz.co.scuff.data.journey.Journey;
import nz.co.scuff.data.place.Place;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 3/06/2015.
 */
@Entity
public abstract class Coordinator extends ModifiableEntity implements Snapshotable, Comparable {

    // routes
    // friends
    // places
    // currentJourneys
    // pastJourneys

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="CoordinatorId")
    protected long coordinatorId;

    // one to many
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="coordinator_routes",
            joinColumns={@JoinColumn(name="CoordinatorId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="RouteId", referencedColumnName="routeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Route> routes;

    // many to many
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="coordinator_places",
            joinColumns={@JoinColumn(name="CoordinatorId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="PlaceId", referencedColumnName="placeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Place> places;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="coordinator_friends",
            joinColumns={@JoinColumn(name="ThisFriendId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="ThatFriendId", referencedColumnName="coordinatorId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Coordinator> friends;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="coordinator_pastjourneys",
            joinColumns={@JoinColumn(name="CoordinatorId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Journey> pastJourneys;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="coordinator_currentjourneys",
            joinColumns={@JoinColumn(name="CoordinatorId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Journey> currentJourneys;

    public Coordinator() {
        super();
    }

    public long getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(long coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public void setPlaces(SortedSet<Place> places) {
        this.places = places;
    }

    public SortedSet<Place> getPlaces() {
        if (places == null) {
            places = new TreeSet<>();
        }
        return places;
    }

    public void setFriends(SortedSet<Coordinator> friends) {
        this.friends = friends;
    }

    public SortedSet<Coordinator> getFriends() {
        if (friends == null) {
            friends = new TreeSet<>();
        }
        return friends;
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

    public SortedSet<Journey> getCurrentJourneys() {
        if (currentJourneys == null) {
            currentJourneys = new TreeSet<>();
        }
        return currentJourneys;
    }

    public void setCurrentJourneys(SortedSet<Journey> journeys) {
        this.currentJourneys = journeys;
    }

    public SortedSet<Journey> getPastJourneys() {
        if (pastJourneys == null) {
            pastJourneys = new TreeSet<>();
        }
        return pastJourneys;
    }

    public void setPastJourneys(SortedSet<Journey> journeys) {
        this.pastJourneys = journeys;
    }

    public abstract CoordinatorSnapshot toSnapshot();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinator c = (Coordinator) o;

        return coordinatorId == c.coordinatorId;

    }

    @Override
    public int hashCode() {
        return (int) (coordinatorId ^ (coordinatorId >>> 32));
    }

    @Override
    public String toString() {
        String s = "Coordinator{" +
                "coordinatorId=" + coordinatorId;
        s += ", routes=";
        if ((routes == null) || routes.isEmpty()) {
            s += "[empty]";
        } else {
            for (Route o : routes) {
                s += " ";
                s += o.getRouteId();
            }
        }
        s += ", places=";
        if ((places == null) || places.isEmpty()) {
            s += "[empty]";
        } else {
            for (Place o : places) {
                s += " ";
                s += o.getPlaceId();
            }
        }
        s += ", friends=";
        if ((friends == null) || friends.isEmpty()) {
            s += "[empty]";
        } else {
            for (Coordinator o : friends) {
                s += " ";
                s += o.getCoordinatorId();
            }
        }
        s += ", pastJourneys=";
        if ((pastJourneys == null) || pastJourneys.isEmpty()) {
            s += "[empty]";
        } else {
            for (Journey o : pastJourneys) {
                s += " ";
                s += o.getJourneyId();
            }
        }
        s += ", currentJourneys=";
        if ((currentJourneys == null) || currentJourneys.isEmpty()) {
            s += "[empty]";
        } else {
            for (Journey o : currentJourneys) {
                s += " ";
                s += o.getJourneyId();
            }
        }
        s += "} " + super.toString();
        return s;
    }
}
