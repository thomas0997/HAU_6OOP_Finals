package Support;
import Users.*;

import java.io.*;
import java.util.*;
import Locations.*;


public class DataManager {
    private String filePath;

    public DataManager(String filePath){this.filePath = filePath;}


    public CampusEntity[] loadCampusData() throws NavigationException {
        List<CampusEntity> list = new ArrayList<>();
        Map<String, Building> buildingMap = new HashMap<>();
        List<String[]> eventRows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip  yung header row
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1); // -1 keeps trailing empty fieldss
                String type = p[0];
                String id = p[1], name = p[2], description = p[3];
                double x = Double.parseDouble(p[4]);
                double y = Double.parseDouble(p[5]);

                switch (type) {
                    case "Admin" -> {
                        int floorCount = Integer.parseInt(p[6]);
                        String[] facilities = p[8].isEmpty() ? new String[]{} : p[8].split(";");
                        AdminBuilding b = new AdminBuilding(name, description, x, y, id, floorCount, facilities, p[7]);
                        list.add(b);
                        buildingMap.put(id, b);
                    }
                    case "Academic" -> {
                        int floorCount = Integer.parseInt(p[6]);
                        String[] facilities = p[8].isEmpty() ? new String[]{} : p[8].split(";");
                        String[] courses = p[7].isEmpty() ? new String[]{} : p[7].split(";");
                        AcademicBuilding b = new AcademicBuilding(name, description, x, y, id, floorCount, facilities, courses);
                        list.add(b);
                        buildingMap.put(id, b);
                    }
                    case "Building" -> {
                        int floorCount = Integer.parseInt(p[6]);
                        String[] facilities = p[8].isEmpty() ? new String[]{} : p[8].split(";");
                        Building b = new Building(name, description, x, y, id, floorCount, facilities);
                        list.add(b);
                        buildingMap.put(id, b);
                    }
                    case "Facility" -> list.add(new Facility(name, description, x, y, id, p[6]));
                    case "Event" -> eventRows.add(p); // hold until all buildings are loaded
                }
            }
        } catch (Exception e) {
            throw new NavigationException(Messages.msg[3]);
        }

        // Second pas, build events and attach them to their building, kung meron
        for (String[] p : eventRows) {
            String id = p[1], name = p[2], description = p[3];
            double x = Double.parseDouble(p[4]);
            double y = Double.parseDouble(p[5]);
            String dateTime = p[6], organizer = p[7], buildingId = p[8];

            Event event = new Event(name, description, x, y, id, dateTime, organizer);
            list.add(event);

            if (!buildingId.isEmpty() && buildingMap.containsKey(buildingId)) {
                buildingMap.get(buildingId).addEvent(event);
            }
        }

        return list.toArray(new CampusEntity[0]);
    }

    public void saveUser(User u) throws NavigationException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true))) {
            String extra = "";
            if (u instanceof Student s) {
                extra = "," + s.getStudentID() + "," + s.getProgram();
            } else if (u instanceof Faculty f) {
                extra = "," + f.getFacultyID();
            } else if (u instanceof Visitor v) {
                extra = "," + v.getPurposeOfVisit();
            }
            writer.write(u.getUserID() + "," + u.getName() + "," + u.getUserType() + extra + "\n");
        } catch (Exception e) {
            throw new NavigationException(Messages.msg[3]);
        }
    }
}