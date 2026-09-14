package Support;
import Users.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private String filePath;

    public DataManager(String filePath){this.filePath = filePath;}

    public void saveStudents(Student[] students) throws NavigationException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false)))
        {
            for (Student s : students){
                writer.write(s.getStudentID() + "," + s.getProgram() + "\n");
            }

        }catch(Exception e){
            throw new NavigationException("ERR003", Messages.msg[3]);
        }
    }

    public Student[] loadStudents() throws NavigationException{
        List<Student> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                list.add(new Student(parts[0], "Student", parts[0], parts[1]));

            }
        }catch (Exception e){
            throw new NavigationException("ERR003", Messages.msg[3]);
        }
        return list.toArray(new Student[0]);
    }
}
