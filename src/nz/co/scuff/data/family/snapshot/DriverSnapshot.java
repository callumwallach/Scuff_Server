package nz.co.scuff.data.family.snapshot;

import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.school.snapshot.RouteSnapshot;
import nz.co.scuff.data.school.snapshot.SchoolSnapshot;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Callum on 4/05/2015.
 */
@XmlRootElement
public class DriverSnapshot extends PersonSnapshot {

    private String email;
    private String phone;

    private SortedSet<SchoolSnapshot> schoolsDrivenFor;
    private SortedSet<PassengerSnapshot> children;
    private SortedSet<RouteSnapshot> registeredRoutes;
    private SortedSet<JourneySnapshot> journeys;

    public DriverSnapshot() {
        schoolsDrivenFor = new TreeSet<>();
        children = new TreeSet<>();
        registeredRoutes = new TreeSet<>();
        journeys = new TreeSet<>();
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

    public SortedSet<SchoolSnapshot> getSchoolsDrivenFor() {
        return schoolsDrivenFor;
    }

    public void setSchoolsDrivenFor(SortedSet<SchoolSnapshot> schoolsDrivenFor) {
        this.schoolsDrivenFor = schoolsDrivenFor;
    }

    public SortedSet<PassengerSnapshot> getChildren() {
        return children;
    }

    public void setChildren(SortedSet<PassengerSnapshot> children) {
        this.children = children;
    }

    public SortedSet<RouteSnapshot> getRegisteredRoutes() {
        return registeredRoutes;
    }

    public void setRegisteredRoutes(SortedSet<RouteSnapshot> registeredRoutes) {
        this.registeredRoutes = registeredRoutes;
    }

    public SortedSet<JourneySnapshot> getJourneys() {
        return journeys;
    }

    public void setJourneys(SortedSet<JourneySnapshot> journeys) {
        this.journeys = journeys;
    }

    @Override
    public String toString() {
        return "DriverSnapshot{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", schoolsDrivenFor=" + schoolsDrivenFor +
                ", children=" + children +
                ", registeredRoutes=" + registeredRoutes +
                ", journeys=" + journeys +
                "} " + super.toString();
    }
}
