package Locations;

public class Event extends CampusEntity {
    private String dateTime;
    private String organizer;

    public Event(String id, String name, String description, double x, double y, String dateTime, String organizer) {
        super(id, name, description, x, y);
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