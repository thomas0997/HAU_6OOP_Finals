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


    public static CampusEntity[] loadCampusData() throws NavigationException {
        DataManager campusManager = new DataManager("Data/CampusData.csv");
        return campusManager.loadCampusData();
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
        try {
            campus = loadCampusData();
        } catch (NavigationException e) {
            System.out.println("Error: " + e.getMessage());
            return; // can't run without campus data
        }
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
