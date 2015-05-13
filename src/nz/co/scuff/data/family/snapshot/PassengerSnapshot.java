package nz.co.scuff.data.family.snapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class PassengerSnapshot extends PersonSnapshot {

    private Set<Long> schoolIds;
    private Set<Long> parentIds;
    private Set<String> boardingInfoIds;
    private Set<Long> registeredRouteIds;

    public PassengerSnapshot() {
        schoolIds = new HashSet<>();
        parentIds = new HashSet<>();
        boardingInfoIds = new HashSet<>();
        registeredRouteIds = new HashSet<>();
    }

    public Set<Long> getSchoolIds() {
        return schoolIds;
    }

    public void setSchools(Set<Long> schoolIds) {
        this.schoolIds = schoolIds;
    }

    public Set<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(Set<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public Set<String> getBoardingInfoIds() {
        return boardingInfoIds;
    }

    public void setBoardingInfoIds(Set<String> boardingInfoIds) {
        this.boardingInfoIds = boardingInfoIds;
    }

    public Set<Long> getRegisteredRouteIds() {
        return registeredRouteIds;
    }

    public void setRegisteredRouteIds(Set<Long> registeredRouteIds) {
        this.registeredRouteIds = registeredRouteIds;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PassengerSnapshot{");
        sb.append("schoolIds=").append(schoolIds);
        sb.append(", parentIds=").append(parentIds);
        sb.append(", boardingInfoIds=").append(boardingInfoIds);
        sb.append(", registeredRouteIds=").append(registeredRouteIds);
        sb.append('}');
        return sb.toString();
    }
}
