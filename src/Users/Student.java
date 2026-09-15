package Users;
import Root.Main;

public class Student extends User{
    private String studentId, program;
    
    public Student(String usertype, String studentId, String program) {
        super(usertype);
        this.studentId = studentId;
        this.program = program;
    }
    public String getStudentID() {
        return studentId;
    }

    public String getProgram() {
        return program;
    }
    @Override 
    public void navigate() {
        System.out.println("Student access: full location + course info ("+ program +")");
        for (Locations.CampusEntity e : Main.campus){
            if (!(e instanceof Locations.AdminBuilding)){
                System.out.println(e.getInfo());
            }
        }
    }

    @Override
    public boolean authenticate() {
        return true;
    } 
}
