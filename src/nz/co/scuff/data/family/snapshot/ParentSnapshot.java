package nz.co.scuff.data.family.snapshot;

import nz.co.scuff.data.school.snapshot.RouteSnapshot;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class ParentSnapshot extends PersonSnapshot {

    private String email;
    private String phone;

    private SortedSet<SchoolSnapshot> schools;
    private SortedSet<ChildSnapshot> children;
    private SortedSet<RouteSnapshot> routes;

    public ParentSnapshot() {
        schools = new TreeSet<>();
        children = new TreeSet<>();
        routes = new TreeSet<>();
    }

    public long getParentId() {
        return super.getPersonId();
    }

    public void setParentId(long parentId) {
        super.setPersonId(parentId);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SortedSet<SchoolSnapshot> getSchools() {
        return schools;
    }

    public void setSchools(SortedSet<SchoolSnapshot> schools) {
        this.schools = schools;
    }

    public SortedSet<ChildSnapshot> getChildren() {
        return children;
    }

    public void setChildren(SortedSet<ChildSnapshot> children) {
        this.children = children;
    }

    public SortedSet<RouteSnapshot> getRoutes() {
        return routes;
    }

    public void setRoutes(SortedSet<RouteSnapshot> route) {
        this.routes = route;
    }

    @Override
    public String toString() {
        return "ParentSnapshot{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", schools=" + schools +
                ", children=" + children +
                ", route=" + routes +
                "} " + super.toString();
    }
}
