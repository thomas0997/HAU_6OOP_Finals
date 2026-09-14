package Locations;

public class Event extends CampusEntity {
    private String dateTime;
    private String organizer;



    public Event(String name, String description, double x, double y, String id, String dateTime, String organizer) {
        super(name, description, x, y, id);
        this.dateTime = dateTime;
        this.organizer = organizer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    @Override
    public String getInfo() {
        return "[Event] " + getName() + " | " + dateTime + " | Organizer: " + organizer;
    }
}