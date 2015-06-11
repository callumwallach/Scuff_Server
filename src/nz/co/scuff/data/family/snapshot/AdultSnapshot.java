package nz.co.scuff.data.family.snapshot;

import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.family.PersonalData;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class AdultSnapshot extends CoordinatorSnapshot {

    private PersonalData personalData;

    private Set<Long> childrenIds;
    private Set<Long> guideeIds;

    public AdultSnapshot() {
        super();
        childrenIds = new HashSet<>();
        guideeIds = new HashSet<>();
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Set<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(Set<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Set<Long> getGuideeIds() {
        return guideeIds;
    }

    public void setGuideeIds(Set<Long> guideeIds) {
        this.guideeIds = guideeIds;
    }

    @Override
    public String toString() {
        return "AdultSnapshot{" +
                "personalData=" + personalData +
                ", childrenIds=" + childrenIds +
                ", guideeIds=" + guideeIds +
                "} " + super.toString();
    }
}
