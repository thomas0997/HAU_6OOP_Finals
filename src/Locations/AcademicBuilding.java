package Locations;

public class AcademicBuilding extends Building {
    private String[] coursesOffered;




    public AcademicBuilding(String name, String description, double x, double y, String id, int floorCount, String[] facilities, String[] coursesOffered) {
        super(name, description, x, y, id, floorCount, facilities);
        this.coursesOffered = coursesOffered;
    }


    @Override
    public String getInfo() {
        return "[Academic Building] " + getName() + " | Courses: " + String.join(", ", coursesOffered) +
            " | Facilities: " + String.join(", ", getFacilities());
    }
}