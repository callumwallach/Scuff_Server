package nz.co.scuff.data.family;

import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.school.Route;
import nz.co.scuff.data.school.School;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 17/03/2015.
 */
@XmlRootElement
@Entity
public class Parent extends Person {

    @NotNull
    @Column(name="Email")
    private String email;
    @NotNull
    @Column(name="Phone")
    private String phone;

    // many to many
    @ManyToMany(mappedBy="parents", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<School> schools;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="parents_children",
            joinColumns={@JoinColumn(name="ParentId", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="ChildId", referencedColumnName="id")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Child> children;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="parents_routes",
            joinColumns={@JoinColumn(name="ParentId", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="RouteId", referencedColumnName="routeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Route> routes;

    public Parent() { }

    public Parent(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }

    public Parent(String firstName, String lastName, Gender gender, String picture) {
        super(firstName, lastName, gender, picture);
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

    public SortedSet<School> getSchools() {
        if (schools == null) {
            schools = new TreeSet<>();
        }
        return schools;
    }

    public void setSchools(SortedSet<School> schools) {
        this.schools = schools;
    }

    public SortedSet<Child> getChildren() {
        if (children == null) {
            children = new TreeSet<>();
        }
        return children;
    }

    public void setChildren(SortedSet<Child> children) {
        this.children = children;
    }

    public SortedSet<Route> getRoutes() {
        if (routes == null) {
            routes = new TreeSet<>();
        }
        return routes;
    }

    public void setRoutes(SortedSet<Route> routes) {
        this.routes = routes;
    }

    public ParentSnapshot toSnapshot() {
        ParentSnapshot snapshot = new ParentSnapshot();
        snapshot.setId(id);
        snapshot.setFirstName(firstName);
        snapshot.setMiddleName(middleName);
        snapshot.setLastName(lastName);
        snapshot.setGender(gender);
        snapshot.setPicture(picture);
        snapshot.setEmail(email);
        snapshot.setPhone(phone);
        return snapshot;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("Parent{");
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }

}