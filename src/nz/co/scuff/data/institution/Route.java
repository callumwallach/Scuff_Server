package nz.co.scuff.data.institution;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.base.ModifiableEntity;
import nz.co.scuff.data.base.Snapshotable;
import nz.co.scuff.data.institution.snapshot.RouteSnapshot;
import nz.co.scuff.data.place.Place;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Route extends ModifiableEntity implements Snapshotable, Comparable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="RouteId")
    private long routeId;
    @NotNull
    @Column(name="Name")
    private String name;
    @NotNull
    @Column(name="RouteMap")
    private String routeMap;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Origin")
    private Place origin;
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="Destination")
    private Place destination;
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Owner")
    private Coordinator owner;

    public Route() {
        super();
    }

    public Route(String name, String routeMap, Coordinator owner,
                 Place origin, Place destination) {
        super();
        this.name = name;
        this.routeMap = routeMap;
        this.owner = owner;
        this.origin = origin;
        this.destination = destination;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteMap() {
        return routeMap;
    }

    public void setRouteMap(String routeMap) {
        this.routeMap = routeMap;
    }

    public Coordinator getOwner() {
        return owner;
    }

    public void setOwner(Coordinator coordinator) {
        this.owner = coordinator;
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

    public RouteSnapshot toSnapshot() {
        RouteSnapshot snapshot = new RouteSnapshot();
        snapshot.setRouteId(routeId);
        snapshot.setName(name);
        snapshot.setRouteMap(routeMap);
        snapshot.setOriginId(origin.getPlaceId());
        snapshot.setDestinationId(destination.getPlaceId());
        snapshot.setOwnerId(owner.getCoordinatorId());
        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        return routeId == route.routeId;
    }

    @Override
    public int hashCode() {
        return (int) (routeId ^ (routeId >>> 32));
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", name='" + name + '\'' +
                ", routeMap='" + routeMap + '\'' +
                ", origin=" + origin.getPlaceId() +
                ", destination=" + destination.getPlaceId() +
                ", owner=" + owner.getCoordinatorId() +
                "} " + super.toString();
    }

    @Override
    public int compareTo(Object another) {
        Route other = (Route)another;
        if (this.equals(other)) return 0;
        return other.name == null ? 1 : this.name.compareTo(other.name);

    }
}