package Users;
import Root.Main;

public class Faculty extends User {
    private String password, facultyID;

    public Faculty (String usertype, String password, String facultyID) {
        super(usertype);
        this.password = password;
        this.facultyID = facultyID;
    }

    public String getFacultyID() {
        return facultyID;
    }
    
    @Override 
    public void navigate(){
        System.out.println("Faculty access: full locations + office hours");
        for (Locations.CampusEntity e : Main.campus){
            System.out.println(e.getInfo());
        }
    }

    @Override 
    public boolean authenticate() {
        if (password.equals("faculty123")) {
            return true;
        } else {
            return false;
        }
    }
}
