package nz.co.scuff.data.family;

import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.SchoolSnapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class UserSnapshot {

    private ParentSnapshot parent;
    private SortedSet<ChildSnapshot> children;
    private SortedSet<SchoolSnapshot> schools;
    private SortedSet<Route> routes;

    public UserSnapshot() {
        children = new TreeSet<>();
        schools = new TreeSet<>();
        routes = new TreeSet<>();
    }

    public ParentSnapshot getParent() {
        return parent;
    }

    public void setParent(ParentSnapshot parent) {
        this.parent = parent;
    }

    public SortedSet<ChildSnapshot> getChildren() {
        return children;
    }

    public void setChildren(SortedSet<ChildSnapshot> children) {
        this.children = children;
    }

    public SortedSet<SchoolSnapshot> getSchools() {
        return schools;
    }

    public void setSchools(SortedSet<SchoolSnapshot> schools) {
        this.schools = schools;
    }

    public SortedSet<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(SortedSet<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "UserSnapshot{" +
                "parent=" + parent +
                ", children=" + children +
                ", schools=" + schools +
                ", routes=" + routes +
                '}';
    }
}
