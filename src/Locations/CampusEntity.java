package Locations;

public abstract class CampusEntity implements Navigable{
    private String name, description, id;
    private double x, y;

    public CampusEntity(String name, String description, double x, double y, String id) {
        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
        this.id = id;
    }


    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getX() { return x; }
    public double getY() { return y; }
    public String getId() { return id; }

    public abstract String getInfo();
    
    public String getDirections() {
        return "Directions to " + name + ": (X: " + x + ", Y: " + y + ")";
    }

}
