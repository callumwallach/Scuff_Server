package nz.co.scuff.data.journey;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.base.ModifiableEntity;
import nz.co.scuff.data.base.Snapshotable;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.institution.Route;
import nz.co.scuff.data.place.Place;
import nz.co.scuff.data.util.Constants;
import nz.co.scuff.data.util.TrackingState;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 20/04/2015.
 */
@Entity
public class Journey extends ModifiableEntity implements Snapshotable, Comparable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="JourneyId")
    private long journeyId;
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
    @JoinColumn(name="Route")
    private Route route;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Owner")
    private Coordinator owner;
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Agent")
    private Coordinator agent;
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Guide")
    private Adult guide;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Origin")
    private Place origin;
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Destination")
    private Place destination;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="JourneyId", referencedColumnName="JourneyId")
    @OrderBy("created DESC")
    @Sort(type = SortType.NATURAL)
    private SortedSet<Waypoint> waypoints;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="journey_issuedtickets",
            joinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")},
            inverseJoinColumns={@JoinColumn(name="TicketId", referencedColumnName="ticketId")})
    @OrderBy("issueDate DESC")
    @Sort(type = SortType.NATURAL)
    private SortedSet<Ticket> issuedTickets;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="journey_stampedtickets",
            joinColumns={@JoinColumn(name="JourneyId", referencedColumnName="journeyId")},
            inverseJoinColumns={@JoinColumn(name="TicketId", referencedColumnName="ticketId")})
    @OrderBy("issueDate DESC")
    @Sort(type = SortType.NATURAL)
    private SortedSet<Ticket> stampedTickets;

    public Waypoint getMostRecentWaypoint() {
        return waypoints.last();
    }

    public Journey() {
        super();
        waypoints = new TreeSet<>();
        issuedTickets = new TreeSet<>();
        stampedTickets = new TreeSet<>();
    }

    public Journey(JourneySnapshot snapshot) {
        this();
        //this.journeyId = snapshot.getJourneyId();
        this.appId = snapshot.getAppId();
        this.source = snapshot.getSource();
        this.totalDistance = snapshot.getTotalDistance();
        this.totalDuration = snapshot.getTotalDuration();
        this.created = snapshot.getCreated();
        this.completed = snapshot.getCompleted();
        this.state = snapshot.getState();

        this.active = snapshot.isActive();
        this.lastModified = snapshot.getLastModified();
    }

    public long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(long journeyId) {
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

    public Coordinator getOwner() {
        return owner;
    }

    public void setOwner(Coordinator owner) {
        this.owner = owner;
    }

    public Coordinator getAgent() {
        return agent;
    }

    public void setAgent(Coordinator agent) {
        this.agent = agent;
    }

    public Adult getGuide() {
        return guide;
    }

    public void setGuide(Adult guide) {
        this.guide = guide;
    }

    public Place getOrigin() {
        return origin;
    }

    public void setOrigin(Place origin) {
        this.origin = origin;
    }

    public Place getDestination() {
        return destination;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
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

    public SortedSet<Ticket> getIssuedTickets() {
        if (issuedTickets == null) {
            issuedTickets = new TreeSet<>();
        }
        return issuedTickets;
    }

    public void setIssuedTickets(SortedSet<Ticket> tickets) {
        this.issuedTickets = tickets;
    }

    public SortedSet<Ticket> getStampedTickets() {
        if (stampedTickets == null) {
            stampedTickets = new TreeSet<>();
        }
        return stampedTickets;
    }

    public void setStampedTickets(SortedSet<Ticket> tickets) {
        this.stampedTickets = tickets;
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
        // entities
        snapshot.setOwnerId(owner.getCoordinatorId());
        snapshot.setAgentId(agent.getCoordinatorId());
        snapshot.setGuideId(guide.getCoordinatorId());
        snapshot.setOriginId(origin.getPlaceId());
        snapshot.setDestinationId(destination.getPlaceId());
        snapshot.setRouteId(route.getRouteId());

        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);

        snapshot.setIssuedTicketIds(Constants.LONG_COLLECTION_NOT_RETRIEVED_PLACEHOLDER);
        snapshot.setStampedTicketIds(Constants.LONG_COLLECTION_NOT_RETRIEVED_PLACEHOLDER);
        snapshot.setWaypointIds(Constants.LONG_COLLECTION_NOT_RETRIEVED_PLACEHOLDER);

        return snapshot;
    }

    @Override
    public int compareTo(Object another) {
        Journey other = (Journey)another;
        if (other.created == null) return 1;
        if (this.created == null) return -1;
        if (this.equals(other)) return 0;
        return this.created.compareTo(other.created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey that = (Journey) o;

        return journeyId == that.journeyId;

    }

    @Override
    public int hashCode() {
        return (int) (journeyId ^ (journeyId >>> 32));
    }

    @Override
    public String toString() {
        String s = "Journey{" +
                "journeyId='" + journeyId + '\'' +
                ", appId='" + appId + '\'' +
                ", source='" + source + '\'' +
                ", totalDistance=" + totalDistance +
                ", totalDuration=" + totalDuration +
                ", created=" + created +
                ", completed=" + completed +
                ", state=" + state +
                ", route=" + route.getRouteId() +
                ", owner=" + owner.getCoordinatorId() +
                ", agent=" + agent.getCoordinatorId() +
                ", guide=" + guide.getCoordinatorId() +
                ", origin=" + origin.getPlaceId() +
                ", destination=" + destination.getPlaceId() +
                ", waypoints=" + waypoints;
        s += ", issuedTickets=";
        if ((issuedTickets == null) || issuedTickets.isEmpty()) {
            s += "[empty]";
        } else {
            for (Ticket o : issuedTickets) {
                s += " ";
                s += o.getTicketId();
            }
        }
        s += "} ";
        s += ", stampedTickets=";
        if ((stampedTickets == null) || stampedTickets.isEmpty()) {
            s += "[empty]";
        } else {
            for (Ticket o : stampedTickets) {
                s += " ";
                s += o.getTicketId();
            }
        }
        s += "} " + super.toString();
        return s;
    }
}
