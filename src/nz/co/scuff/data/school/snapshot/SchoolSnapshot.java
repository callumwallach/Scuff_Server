package nz.co.scuff.data.school.snapshot;

import nz.co.scuff.data.family.snapshot.ChildSnapshot;
import nz.co.scuff.data.family.snapshot.ParentSnapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class SchoolSnapshot implements Comparable {

    private long schoolId;
    private String name;
    private double latitude;
    private double longitude;
    private double altitude;

    private SortedSet<RouteSnapshot> routes;
    private SortedSet<ChildSnapshot> children;
    private SortedSet<ParentSnapshot> parents;

    public SchoolSnapshot() {
        routes = new TreeSet<>();
        children = new TreeSet<>();
        parents = new TreeSet<>();
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

    public SortedSet<RouteSnapshot> getRoutes() {
        return routes;
    }

    public void setRoutes(SortedSet<RouteSnapshot> routes) {
        this.routes = routes;
    }

    public SortedSet<ChildSnapshot> getChildren() {
        return children;
    }

    public void setChildren(SortedSet<ChildSnapshot> children) {
        this.children = children;
    }

    public SortedSet<ParentSnapshot> getParents() {
        return parents;
    }

    public void setParents(SortedSet<ParentSnapshot> parents) {
        this.parents = parents;
    }

    @Override
    public int compareTo(Object another) {
        SchoolSnapshot other = (SchoolSnapshot)another;
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchoolSnapshot that = (SchoolSnapshot) o;

        return schoolId == that.schoolId;

    }

    @Override
    public int hashCode() {
        return (int) (schoolId ^ (schoolId >>> 32));
    }

    @Override
    public String toString() {
        return "SchoolSnapshot{" +
                "schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", routes=" + routes +
                ", children=" + children +
                ", parents=" + parents +
                '}';
    }
}
