package Support;
import Users.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import Locations.AcademicBuilding;
import Locations.AdminBuilding;
import Locations.Building;
import Locations.CampusEntity;
import Locations.Event;
import Locations.Facility;

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
            throw new NavigationException(Messages.msg[3]);
        }
    }

    public Student[] loadStudents() throws NavigationException{
        List<Student> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                list.add(new Student(parts[0], "Student", parts[1]));

            }
        }catch (Exception e){
            throw new NavigationException(Messages.msg[3]);
        }
        return list.toArray(new Student[0]);
    }


    public CampusEntity[] loadCampusData() throws NavigationException {
        List<CampusEntity> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip header row
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1); // -1 keeps trailing empty fields
                String type = p[0];
                String id = p[1], name = p[2], description = p[3];
                double x = Double.parseDouble(p[4]);
                double y = Double.parseDouble(p[5]);

                switch (type) {
                    case "Admin" -> {
                        int floorCount = Integer.parseInt(p[6]);
                        list.add(new AdminBuilding(name, description, x, y, id,
                                floorCount, new String[]{}, p[7]));
                    }
                    case "Academic" -> {
                        int floorCount = Integer.parseInt(p[6]);
                        list.add(new AcademicBuilding(name, description, x, y, id,
                                floorCount, new String[]{}, p[7].split(";")));
                    }
                    case "Building" -> {
                        int floorCount = Integer.parseInt(p[6]);
                        list.add(new Building(name, description, x, y, id,
                                floorCount, new String[]{}));
                    }
                    case "Facility" -> list.add(new Facility(name, description, x, y, id, p[6]));
                    case "Event" -> list.add(new Event(name, description, x, y, id, p[6], p[7]));
                }
            }
        } catch (Exception e) {
            throw new NavigationException(Messages.msg[3]);
        }
        return list.toArray(new CampusEntity[0]);
    }
}
