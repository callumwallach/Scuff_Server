package nz.co.scuff.data.family;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.family.snapshot.ChildSnapshot;
import nz.co.scuff.data.school.School;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 17/03/2015.
 */
@XmlRootElement
@Entity
public class Child extends Person {

    // many to many
    @ManyToMany(mappedBy="children", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<School> schools;

    @ManyToMany(mappedBy="children", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<Parent> parents;

    public Child() { }

    public Child(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }

    public Child(String firstName, String lastName, Gender gender, String picture) {
        super(firstName, lastName, gender, picture);
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

    public SortedSet<Parent> getParents() {
        if (parents == null) {
            parents = new TreeSet<>();
        }
        return parents;
    }

    public void setParents(SortedSet<Parent> parents) {
        this.parents = parents;
    }

    public ChildSnapshot toSnapshot() {
        ChildSnapshot snapshot = new ChildSnapshot();
        snapshot.setPersonId(personId);
        snapshot.setFirstName(firstName);
        snapshot.setMiddleName(middleName);
        snapshot.setLastName(lastName);
        snapshot.setGender(gender);
        snapshot.setPicture(picture);
        return snapshot;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("Child{");
        sb.append('}');
        return sb.toString();
    }

}