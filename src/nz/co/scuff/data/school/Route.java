package nz.co.scuff.data.school;

import nz.co.scuff.data.school.snapshot.RouteSnapshot;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Route implements Comparable, Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="RouteId")
    private long routeId;
    @Column(name="Name")
    private String name;
    @Column(name="RouteMap")
    private String routeMap;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="school")
    private School school;

    public Route() {}

    public Route(String name, String routeMap, School school) {
        this.name = name;
        this.routeMap = routeMap;
        this.school = school;
    }

    public Route(RouteSnapshot snapshot) {
        this.routeId = snapshot.getRouteId();
        this.name = snapshot.getName();
        this.routeMap = snapshot.getRouteMap();
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public RouteSnapshot toSnapshot() {
        RouteSnapshot snapshot = new RouteSnapshot();
        snapshot.setRouteId(routeId);
        snapshot.setName(name);
        snapshot.setRouteMap(routeMap);
        return snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (routeId != route.routeId) return false;
        if (name != null ? !name.equals(route.name) : route.name != null) return false;
        return !(routeMap != null ? !routeMap.equals(route.routeMap) : route.routeMap != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (routeId ^ (routeId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (routeMap != null ? routeMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", name='" + name + '\'' +
                ", routeMap='" + routeMap + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        Route other = (Route)another;
        return other.name == null ? 1 : this.name.compareTo(other.name);

    }
}