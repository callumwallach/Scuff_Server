package nz.co.scuff.data.family.snapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class DriverSnapshot extends PersonSnapshot {

    private String email;
    private String phone;

    private Set<Long> schoolIdsDrivenFor;
    private Set<Long> childrenIds;
    private Set<Long> registeredRouteIds;
    private Set<Long> journeyIds;

    public DriverSnapshot() {
        schoolIdsDrivenFor = new HashSet<>();
        childrenIds = new HashSet<>();
        registeredRouteIds = new HashSet<>();
        journeyIds = new HashSet<>();
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

    public Set<Long> getSchoolIdsDrivenFor() {
        return schoolIdsDrivenFor;
    }

    public void setSchoolIdsDrivenFor(Set<Long> schoolIdsDrivenFor) {
        this.schoolIdsDrivenFor = schoolIdsDrivenFor;
    }

    public Set<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(Set<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Set<Long> getRegisteredRouteIds() {
        return registeredRouteIds;
    }

    public void setRegisteredRouteIds(Set<Long> registeredRouteIds) {
        this.registeredRouteIds = registeredRouteIds;
    }

    public Set<Long> getJourneyIds() {
        return journeyIds;
    }

    public void setJourneyIds(Set<Long> journeyIds) {
        this.journeyIds = journeyIds;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DriverSnapshot{");
        sb.append("email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", schoolIdsDrivenFor=").append(schoolIdsDrivenFor);
        sb.append(", childrenIds=").append(childrenIds);
        sb.append(", registeredRouteIds=").append(registeredRouteIds);
        sb.append(", journeyIds=").append(journeyIds);
        sb.append('}');
        return sb.toString();
    }
}
