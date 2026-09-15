package Locations;

public class Building extends CampusEntity {
    private int floorCount;
    private String[] facilities;

    public Building(String name, String description, double x, double y, String id, int floorCount, String[] facilities) {
        super(name, description, x, y, id);
        this.floorCount = floorCount;
        this.facilities = facilities;
    }

    public String[] getFacilities() { return facilities; } 


    @Override
    public String getInfo() {
        return "[Building] " + getName() + " | Floors: " + floorCount +
            " | Facilities: " + String.join(", ", facilities);
    }
}