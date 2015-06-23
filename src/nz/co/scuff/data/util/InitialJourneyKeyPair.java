package nz.co.scuff.data.util;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 15/06/2015.
 */
@XmlRootElement
public class InitialJourneyKeyPair {

    public long journeyId;
    public long waypointId;

    public InitialJourneyKeyPair(long journeyId, long waypointId) {
        this.journeyId = journeyId;
        this.waypointId = waypointId;
    }

    @Override
    public String toString() {
        return "InitialJourneyKeyPair{" +
                "journeyId=" + journeyId +
                ", waypointId=" + waypointId +
                '}';
    }
}
