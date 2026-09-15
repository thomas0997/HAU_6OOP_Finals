package Locations;

public class AdminBuilding extends Building {
    private String officeHours;

    public AdminBuilding(String name, String description, double x, double y, String id, int floorCount, String[] facilities, String officeHours) {
        super(name, description, x, y, id, floorCount, facilities);
        this.officeHours = officeHours;
    }

    @Override
    public String getInfo() {
        return "[Admin Building] " + getName() + " | Hours: " + officeHours;
    }
}