package nz.co.scuff.data.family;

import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.family.snapshot.AdultSnapshot;
import nz.co.scuff.data.institution.Institution;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Adult extends Coordinator {

/*    @NotNull
    private Schedule schedule;*/
    @NotNull
    @Embedded
    private PersonalData personalData;

/*    @ManyToMany(mappedBy="drivers", fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private SortedSet<Institution> schoolsDrivingFor;*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="adult_children",
            joinColumns={@JoinColumn(name="AdultId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="ChildId", referencedColumnName="childId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Child> children;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="adult_guidees",
            joinColumns={@JoinColumn(name="AdultId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="InstitutionId", referencedColumnName="coordinatorId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Institution> guidees;

    public Adult() {
        super();
    }

    public Adult(PersonalData data) {
        super();
        this.personalData = data;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public void setChildren(SortedSet<Child> children) {
        this.children = children;
    }

    public SortedSet<Child> getChildren() {
        if (children == null) {
            children = new TreeSet<>();
        }
        return children;
    }

    public SortedSet<Institution> getGuidees() {
        if (guidees == null) {
            guidees = new TreeSet<>();
        }
        return guidees;
    }

    public void setGuidees(SortedSet<Institution> guidees) {
        this.guidees = guidees;
    }

    public AdultSnapshot toSnapshot() {
        AdultSnapshot snapshot = new AdultSnapshot();
        snapshot.setCoordinatorId(coordinatorId);
        snapshot.setPersonalData(personalData);
        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);
        return snapshot;
    }

    @Override
    public int compareTo(Object another) {
        Adult that = (Adult)another;
        return this.personalData.compareTo(that.personalData);
    }

    @Override
    public String toString() {
        String s =  "Adult{" +
                "personalData=" + personalData +
                ", children=";
        if ((children == null) || children.isEmpty()) {
            s += "[empty]";
        } else {
            for (Child c : children) {
                s += " ";
                s += c.getChildId();
            }
        }
        s += ", guidees=";
        if ((guidees == null) || guidees.isEmpty()) {
            s += "[empty]";
        } else {
            for (Institution i : guidees) {
                s += " ";
                s += i.getCoordinatorId();
            }
        }
        s+= "} " + super.toString();
        return s;
    }
}