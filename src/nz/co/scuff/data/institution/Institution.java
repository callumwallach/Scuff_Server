package nz.co.scuff.data.institution;

import java.util.SortedSet;
import java.util.TreeSet;

import nz.co.scuff.data.base.Coordinator;
import nz.co.scuff.data.family.Adult;
import nz.co.scuff.data.institution.snapshot.InstitutionSnapshot;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Callum on 17/03/2015.
 */
@Entity
public class Institution extends Coordinator {

    // one to many
/*    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="school_routes",
            joinColumns={@JoinColumn(name="SchoolId", referencedColumnName="schoolId")},
            inverseJoinColumns={@JoinColumn(name="RouteId", referencedColumnName="routeId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Route> routes;*/

    @NotNull
    @Embedded
    private InstitutionData institutionData;

    // many to many
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="institution_guides",
            joinColumns={@JoinColumn(name="InstitutionId", referencedColumnName="coordinatorId")},
            inverseJoinColumns={@JoinColumn(name="GuideId", referencedColumnName="coordinatorId")})
    @Sort(type = SortType.NATURAL)
    private SortedSet<Adult> guides;

    public Institution() {
        super();
    }

    public Institution(InstitutionData data) {
        super();
        this.institutionData = data;
    }

    public SortedSet<Adult> getGuides() {
        if (guides == null) {
            guides = new TreeSet<>();
        }
        return guides;
    }

    public void setGuides(SortedSet<Adult> adults) {
        this.guides = adults;
    }

    public InstitutionSnapshot toSnapshot() {
        InstitutionSnapshot snapshot = new InstitutionSnapshot();
        snapshot.setCoordinatorId(coordinatorId);
        snapshot.setInstitutionData(institutionData);
        snapshot.setActive(active);
        snapshot.setLastModified(lastModified);
        return snapshot;
    }

    @Override
    public String toString() {
        String s = "Institution{" +
                "institutionData=" + institutionData;
        s += ", guides=";
        if ((guides == null) || guides.isEmpty()) {
            s += "[empty]";
        } else {
            for (Adult a : guides) {
                s += " ";
                s += a.getCoordinatorId();
            }
        }
        s += "} " + super.toString();
        return s;
    }

    @Override
    public int compareTo(Object another) {
        Institution that = (Institution)another;
        return this.institutionData.compareTo(that.institutionData);

    }
}