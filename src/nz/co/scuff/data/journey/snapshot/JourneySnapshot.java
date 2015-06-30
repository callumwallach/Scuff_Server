package nz.co.scuff.data.journey.snapshot;

import nz.co.scuff.data.base.snapshot.ModifiableSnapshot;
import nz.co.scuff.data.util.TrackingState;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 20/04/2015.
 */
@XmlRootElement
public class JourneySnapshot extends ModifiableSnapshot {

    private long journeyId;
    private String appId;
    private String source;
    private float totalDistance;
    private long totalDuration;
    private Timestamp created;
    private Timestamp completed;
    private TrackingState state;

    private long ownerId;
    private long agentId;
    private long guideId;

    private long originId;
    private long destinationId;

    private long routeId;

    private Set<Long> waypointIds;
    private Set<Long> issuedTicketIds;
    private Set<Long> stampedTicketIds;

    public JourneySnapshot() {
        super();
        waypointIds = new HashSet<>();
        issuedTicketIds = new HashSet<>();
        stampedTicketIds = new HashSet<>();
    }

    public long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(long journeyId) {
        this.journeyId = journeyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getCompleted() {
        return completed;
    }

    public void setCompleted(Timestamp completed) {
        this.completed = completed;
    }

    public TrackingState getState() {
        return state;
    }

    public void setState(TrackingState state) {
        this.state = state;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public long getGuideId() {
        return guideId;
    }

    public void setGuideId(long guideId) {
        this.guideId = guideId;
    }

    public long getOriginId() {
        return originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public Set<Long> getWaypointIds() {
        return waypointIds;
    }

    public void setWaypointIds(Set<Long> waypointIds) {
        this.waypointIds = waypointIds;
    }

    public Set<Long> getIssuedTicketIds() {
        return issuedTicketIds;
    }

    public void setIssuedTicketIds(Set<Long> issuedTicketIds) {
        this.issuedTicketIds = issuedTicketIds;
    }

    public Set<Long> getStampedTicketIds() {
        return stampedTicketIds;
    }

    public void setStampedTicketIds(Set<Long> stampedTicketIds) {
        this.stampedTicketIds = stampedTicketIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JourneySnapshot that = (JourneySnapshot) o;

        return journeyId == that.journeyId;

    }

    @Override
    public int hashCode() {
        return (int) (journeyId ^ (journeyId >>> 32));
    }

    @Override
    public String toString() {
        return "JourneySnapshot{" +
                "journeyId='" + journeyId + '\'' +
                ", appId='" + appId + '\'' +
                ", source='" + source + '\'' +
                ", totalDistance=" + totalDistance +
                ", totalDuration=" + totalDuration +
                ", created=" + created +
                ", completed=" + completed +
                ", state=" + state +
                ", ownerId=" + ownerId +
                ", agentId=" + agentId +
                ", guideId=" + guideId +
                ", originId=" + originId +
                ", destinationId=" + destinationId +
                ", routeId=" + routeId +
                ", waypointIds=" + waypointIds +
                ", issuedTicketIds=" + issuedTicketIds +
                ", stampedTicketIds=" + stampedTicketIds +
                '}';
    }

}

