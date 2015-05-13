package nz.co.scuff.data.school.snapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

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

    private Set<Long> routeIds;
    private Set<Long> childrenIds;
    private Set<Long> parentIds;
    private Set<Long> journeyIds;

    public SchoolSnapshot() {
        routeIds = new HashSet<>();
        childrenIds = new HashSet<>();
        parentIds = new HashSet<>();
        journeyIds = new HashSet<>();
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

    public Set<Long> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(Set<Long> routeIds) {
        this.routeIds = routeIds;
    }

    public Set<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(Set<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Set<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(Set<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public Set<Long> getJourneyIds() {
        return journeyIds;
    }

    public void setJourneyIds(Set<Long> journeyIds) {
        this.journeyIds = journeyIds;
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
                ", routeIds=" + routeIds +
                ", childrenIds=" + childrenIds +
                ", parentIds=" + parentIds +
                ", journeyIds=" + journeyIds +
                '}';
    }
}
