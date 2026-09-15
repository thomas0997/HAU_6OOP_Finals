package Root;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.awt.Desktop;

import Users.*;
import Support.*;
import Locations.*;

import java.io.*;
import java.text.*;

public class Main {
    public static CampusEntity[] campus;
    static DecimalFormat df = new DecimalFormat("##,##0.00");
    static Scanner input = new Scanner(System.in);
    public static User login() throws NavigationException{
        System.out.println("""
[1] Faculty
[2] Student
[Other] Visitor
        """);

        int userRole = -1;
        boolean validEntry = false;
        while (!(validEntry)){
            try{
                System.out.print("Enter Choice :  "); userRole = input.nextInt(); input.nextLine();
                validEntry = true;
            }catch (InputMismatchException e){
                System.out.println("Error: " + Messages.msg[4]);
                input.nextLine();
            }
        }

        switch (userRole){
            case 1 -> {

                System.out.print("Enter Faculty ID: "); String facultyID = input.nextLine();
                System.out.print("Enter Password: "); String password = input.nextLine();
                Faculty facultyObject = new Faculty("Faculty", password, facultyID);

                if (!(facultyObject.authenticate())){
                    throw new NavigationException(Messages.msg[5]);
                } 
                return facultyObject;
            }

            case 2 -> {
                System.out.print("Enter Student ID: "); String studentID = input.nextLine();
                System.out.print("Enter Your Program (BS-Cybersecurity, BS-Aeronautical-Engineering, etc...): "); String program = input.nextLine();
                Student studentObject = new Student("Student", studentID, program);

                DataManager dm = new DataManager("Data/Records.csv");
                dm.saveStudents(new Student[]{studentObject});

                return studentObject;
            }


            default -> {
                System.out.print("Enter Purpose of Visit: "); String purpose = input.nextLine();
                System.out.print("Enter What type of ID to Leave: "); String typeOfID = input.nextLine();
                Visitor visitorObject = new Visitor("Visitor", purpose, typeOfID);
                return visitorObject;
            }
        }
    }


    public static CampusEntity[] loadCampusData() {
        return new CampusEntity[] {
    
            new AdminBuilding(
                "Summit Hall",                          // name
                "Main admin building",                  // description
                -8.4, -9.5,                              // x, y
                "B1",                                    // id
                5,                                        // floorCount
                new String[]{"President's Office", "Registrar", "Accounting"}, // facilities
                "8AM-5PM"                                 // officeHours
            ),
    
            new AcademicBuilding(
                "Thomas J Franco Nexus Bldg",
                "ICT college building",
                6, 10,
                "B10",
                4,
                new String[]{"Computer Lab", "Canteen"},
                new String[]{"BSIT", "BSCS", "BSCyberSec"}
            ),
    
            new Building(
                "St. Gabriel Hall",
                "Basic education building",
                5, 5,
                "B9",
                3,
                new String[]{"Library", "Faculty Room"}
            ),
    
            new Facility(
                "Colosseum Gym",
                "Gym, Sports Office, Swimming Pool",
                -8, 10,
                "B5",
                "Gym"
            ),
    
            new Facility(
                "Main Gate",
                "Campus entrance",
                0, -14,
                "B16",
                "Entrance"
            ),
    
            new Event(
                "OOP Project Defense",
                "Defense presentation for 6OOP",
                6, 8,
                "E01",
                "Oct 15, 9AM",
                "CYB-201"
            )
    
        };
    }
    

    public static void showMap(String fileName){
        try{
            File imageFile = new File(fileName);
            if (!(imageFile.exists())){
                throw new IOException(Messages.msg[3]);
            }

            if(Desktop.isDesktopSupported()){
                Desktop.getDesktop().open(imageFile);
                System.out.println("Opening Map...");
            }
        }catch(IOException e){
            System.out.println("\nError: " + Messages.msg[3]);
        }
    }



    public static void main(String[] args) throws NavigationException{
        campus = loadCampusData();
        User currentUser = null;

        // Force login before anything else
        while (currentUser == null) {
            try {
                currentUser = login();
            } catch (NavigationException e) {
                System.out.println("\nError: " + e.getMessage());
            }
        }

        System.out.println("\nWelcome, " + currentUser.getUserType() + "! (ID: " + currentUser.getUserID() + ")");
        currentUser.navigate();

        boolean isRunning = true;
        int userChoice = -1;
        while (isRunning) {
            System.out.println("""
                \nSELECT WHAT YOU WANT TO DO
                [1] Display Campus Map
                [2] View All Locations and Events
                [3] Plan a Route
                [4] Exit""");
            try{
                System.out.print("Enter Choice: ");
                userChoice = input.nextInt();
                input.nextLine();
            }catch(InputMismatchException e){
                System.out.println("\nError: " + Messages.msg[4]);
                input.nextLine();
                continue;
            }
            

            switch (userChoice) {
                case 1 -> showMap("Data/UpdatedCampusMap.png");

                case 2 -> currentUser.navigate(); // shows role-filtered locations again

                case 3 -> {
                    try {
                        System.out.println("\nAvailable locations:");
                        for (CampusEntity e : campus) {
                            System.out.println(e.getId() + " - " + e.getName());
                        }
                        System.out.print("Enter starting location ID: ");
                        String startId = input.nextLine();
                        System.out.print("Enter destination location ID: ");
                        String endId = input.nextLine();

                        CampusEntity start = null, end = null;
                        for (CampusEntity e : campus) {
                            if (e.getId().equals(startId)) start = e;
                            if (e.getId().equals(endId)) end = e;
                        }
                        if (start == null || end == null) {
                            throw new LocationNotFoundException(Messages.msg[1]);
                        }

                        Route route = new Route(new CampusEntity[]{start, end});
                        route.calculateRoute();
                        System.out.println("Total Distance: " + df.format(route.getTotalDistance()));
                    } catch (NavigationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 4 -> {
                    System.out.println("Exiting Program.");
                    System.out.println("Thank you!");
                    isRunning = false;
                }

                default -> System.out.println(Messages.msg[4]);
            }
        }
    }
}
