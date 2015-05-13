package nz.co.scuff.data.school.snapshot;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 17/03/2015.
 */
@XmlRootElement
public class RouteSnapshot implements Comparable {

    private long routeId;
    private String name;
    private String routeMap;
    private long schoolId;

    public RouteSnapshot() {}

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

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public int compareTo(Object another) {
        RouteSnapshot other = (RouteSnapshot)another;
        return this.name.compareTo(other.name);
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
                ", schoolId=" + schoolId +
                '}';
    }

}