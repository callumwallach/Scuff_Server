package nz.co.scuff.data.schedule;

import java.util.HashSet;

/**
 * Created by Callum on 17/03/2015.
 */
public class Schedule {

    public Schedule() {
        this.scheduleItems = new HashSet<ScheduleItem>();
    }

    private HashSet<ScheduleItem> scheduleItems;

    public boolean addScheduleItem(ScheduleItem item) {
        return scheduleItems.add(item);
    }

    public boolean removeScheduleItem(ScheduleItem item) {
        return scheduleItems.remove(item);
    }

    public HashSet<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public void setScheduleItems(HashSet<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

}