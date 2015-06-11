package nz.co.scuff.data.institution.snapshot;

import nz.co.scuff.data.base.snapshot.ModifiableSnapshot;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 17/03/2015.
 */
@XmlRootElement
public class RouteSnapshot extends ModifiableSnapshot {

    private long routeId;
    private String name;
    private String routeMap;
    private long originId;
    private long destinationId;
    private long ownerId;

    public RouteSnapshot() {
        super();
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

    public long getOriginId() {
        return originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteSnapshot that = (RouteSnapshot) o;

        return routeId == that.routeId;

    }

    @Override
    public int hashCode() {
        return (int) (routeId ^ (routeId >>> 32));
    }

    @Override
    public String toString() {
        return "RouteSnapshot{" +
                "routeId=" + routeId +
                ", name='" + name + '\'' +
                ", routeMap='" + routeMap + '\'' +
                ", originId=" + originId +
                ", destinationId=" + destinationId +
                ", ownerId=" + ownerId +
                "} " + super.toString();
    }

}