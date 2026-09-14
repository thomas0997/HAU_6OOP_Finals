package Locations;

public class Event extends CampusEntity {
    private String dateTime;
    private String organizer;



    public Event(String name, String description, double x, double y, String id, String dateTime, String organizer) {
        super(name, description, x, y, id);
        this.dateTime = dateTime;
        this.organizer = organizer;
    }




    @Override
    public String getInfo() {
        return "Event " + getName() + " | " + dateTime + " | Organizer: " + organizer;
    }
}