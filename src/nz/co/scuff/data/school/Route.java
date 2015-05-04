package nz.co.scuff.data.school;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Callum on 17/03/2015.
 */
@XmlRootElement
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

    public Route() {}

    public Route(String name) {
        this.name = name;
        this.routeMap = "Currently not available";
    }

    public Route(String name, String routeMap) {
        this.name = name;
        this.routeMap = routeMap;
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
        final StringBuffer sb = new StringBuffer("Route{");
        sb.append("routeId=").append(routeId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", routeMap='").append(routeMap).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object another) {
        Route other = (Route)another;
        return this.name.compareTo(other.name);
    }
}