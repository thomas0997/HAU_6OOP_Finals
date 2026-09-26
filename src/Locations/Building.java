package Locations;

import java.util.*;

public class Building extends CampusEntity {
    private int floorCount;
    private String[] facilities;
    private List<Event> events = new ArrayList<>();

    public Building(String name, String description, double x, double y, String id, int floorCount, String[] facilities) {
        super(name, description, x, y, id);
        this.floorCount = floorCount;
        this.facilities = facilities;
    }

    public String[] getFacilities() { return facilities; } 
    public void addEvent(Event e){events.add(e);}
    public List<Event> getEvents(){return events;}
 
    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder("[Building] " + getName() + " | Floors: " + floorCount +
                " | Facilities: " + String.join(", ", facilities));
        if (!events.isEmpty()) {
            sb.append(" | Events: ");
            for (Event e : events) sb.append(e.getName()).append("; ");
        }
        return sb.toString();
    }
}