package Users;


public class Faculty extends User {
    private String password, facultyID;

    public Faculty (String userid, String usertype, String password, String facultyID) {
        super(userid, usertype);
        this.password = password;
        this.facultyID = facultyID;
    }

    public String getFacultyID() {
        return facultyID;
    }
    
    @Override 
    public void navigate(){
        System.out.println("Faculty access: full locations + office hours");
    }

    @Override 
    public boolean authenticate () {
        if (password.equals("faculty123")) {
            return true;
        } else {
            return false;
        }
    }
}
