package Users;
import Root.Main;

public class Visitor extends User {
    private String purposeOfVisit, typeOfID;

    public Visitor(String usertype, String purposeOfVisit, String typeOfID) {
        super(usertype);
        this.purposeOfVisit = purposeOfVisit;
        this.typeOfID = typeOfID;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public String getTypeOfID() {
        return typeOfID;
    }

    @Override
    public void navigate() {
        System.out.println("Visitor access: public locations only");
        for (Locations.CampusEntity e : Main.campus) {
            if (e instanceof Locations.Facility || e instanceof Locations.Event) {
                System.out.println(e.getInfo());
            }
        }
    }

    @Override
    public boolean authenticate() {
        return true;
    }
}