package nz.co.scuff.data.institution.snapshot;

import nz.co.scuff.data.base.snapshot.CoordinatorSnapshot;
import nz.co.scuff.data.institution.InstitutionData;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class InstitutionSnapshot extends CoordinatorSnapshot {

    private InstitutionData institutionData;

    private Set<Long> guideIds;

    public InstitutionSnapshot() {
        super();
        guideIds = new HashSet<>();
    }

    public InstitutionData getInstitutionData() {
        return institutionData;
    }

    public void setInstitutionData(InstitutionData institutionData) {
        this.institutionData = institutionData;
    }

    public Set<Long> getGuideIds() {
        return guideIds;
    }

    public void setGuideIds(Set<Long> guideIds) {
        this.guideIds = guideIds;
    }

    @Override
    public String toString() {
        return "InstitutionSnapshot{" +
                "institutionData=" + institutionData +
                ", guideIds=" + guideIds +
                "} " + super.toString();
    }
}
