package Users;

public class Student extends User{
    private String studentId, program;
    
    public Student(String userid, String usertype, String studentId, String program) {
        super(userid, usertype);
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
        System.out.println("Student acccess: full location + course info ("+ program +")");
    }

    @Override
    public boolean authenticate() {
        return true;
    } 
}
