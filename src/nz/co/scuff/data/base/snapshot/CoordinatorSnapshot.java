package nz.co.scuff.data.base.snapshot;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Callum on 3/06/2015.
 */
public abstract class CoordinatorSnapshot extends ModifiableSnapshot {

    private long coordinatorId;
    private Timestamp lastLogin;

    private Set<Long> placeIds;
    private Set<Long> routeIds;
    private Set<Long> friendIds;
    private Set<String> pastJourneyIds;
    private Set<String> currentJourneyIds;

    public CoordinatorSnapshot() {
        super();
        placeIds = new HashSet<>();
        routeIds = new HashSet<>();
        friendIds = new HashSet<>();
        pastJourneyIds = new HashSet<>();
        currentJourneyIds = new HashSet<>();        
    }

    public long getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(long coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<Long> getPlaceIds() {
        return placeIds;
    }

    public void setPlaceIds(Set<Long> placeIds) {
        this.placeIds = placeIds;
    }

    public Set<Long> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(Set<Long> friendIds) {
        this.friendIds = friendIds;
    }

    public Set<String> getPastJourneyIds() {
        return pastJourneyIds;
    }

    public void setPastJourneyIds(Set<String> pastJourneyIds) {
        this.pastJourneyIds = pastJourneyIds;
    }

    public Set<String> getCurrentJourneyIds() {
        return currentJourneyIds;
    }

    public void setCurrentJourneyIds(Set<String> currentJourneyIds) {
        this.currentJourneyIds = currentJourneyIds;
    }

    public Set<Long> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(Set<Long> routeIds) {
        this.routeIds = routeIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordinatorSnapshot that = (CoordinatorSnapshot) o;

        return coordinatorId == that.coordinatorId;

    }

    @Override
    public int hashCode() {
        return (int) (coordinatorId ^ (coordinatorId >>> 32));
    }

    @Override
    public String toString() {
        return "CoordinatorSnapshot{" +
                "coordinatorId=" + coordinatorId +
                ", lastLogin=" + lastLogin +
                ", placeIds=" + placeIds +
                ", routeIds=" + routeIds +
                ", friendIds=" + friendIds +
                ", pastJourneyIds=" + pastJourneyIds +
                ", currentJourneyIds=" + currentJourneyIds +
                "} " + super.toString();
    }
}