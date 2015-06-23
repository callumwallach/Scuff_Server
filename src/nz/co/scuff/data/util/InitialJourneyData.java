package nz.co.scuff.data.util;

import nz.co.scuff.data.journey.snapshot.JourneySnapshot;
import nz.co.scuff.data.journey.snapshot.WaypointSnapshot;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 15/06/2015.
 */
@XmlRootElement
public class InitialJourneyData {

    public JourneySnapshot journeySnapshot;
    public WaypointSnapshot waypointSnapshot;

    public InitialJourneyData(JourneySnapshot journeySnapshot, WaypointSnapshot waypointSnapshot) {
        this.journeySnapshot = journeySnapshot;
        this.waypointSnapshot = waypointSnapshot;
    }

    @Override
    public String toString() {
        return "InitialJourneyData{" +
                "journeySnapshot=" + journeySnapshot +
                ", waypointSnapshot=" + waypointSnapshot +
                '}';
    }
}
