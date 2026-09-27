package Root;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

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

    public static User login() throws NavigationException {
        System.out.println("""
            [1] Faculty
            [2] Student
            [3 or Any Other Number] Visitor
            """);

        int userRole = -1;
        boolean validEntry = false;
        while (!validEntry) {
            try {
                System.out.print("Enter Choice: ");
                userRole = input.nextInt();
                input.nextLine();
                validEntry = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: " + Messages.msg[4]);
                input.nextLine();
            }
        }

        System.out.print("Enter Your Name: ");
        String name = input.nextLine();

        User userObj;

        switch (userRole) {
            case 1 -> {
                System.out.print("Enter Faculty ID: "); String facultyID = input.nextLine();
                System.out.print("Enter Password: "); String password = input.nextLine();
                Faculty facultyObject = new Faculty("Faculty", name, password, facultyID);
                if (!facultyObject.authenticate()) {
                    throw new NavigationException(Messages.msg[5]);
                }
                userObj = facultyObject;
            }
            case 2 -> {
                System.out.print("Enter Student ID: "); String studentID = input.nextLine();
                System.out.print("Enter Your Program (Example: BS_Cybersecurity, BS_Aeronautical_Engineering etc..): "); String program = input.nextLine();
                userObj = new Student("Student", name, studentID, program);
            }
            default -> {
                System.out.print("Enter Purpose of Visit: "); String purpose = input.nextLine();
                System.out.print("Enter What type of ID to Leave: "); String typeOfID = input.nextLine();
                userObj = new Visitor("Visitor", name, purpose, typeOfID);
            }
        }

        DataManager dm = new DataManager("Data/Records.csv");
        dm.saveUser(userObj);

        return userObj;
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
            return; // can't run pag walang campus data
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

        System.out.println("\nWelcome, " + currentUser.getName() + "! (ID: " + currentUser.getUserID() + ")");
        currentUser.navigate();

        boolean isRunning = true;
        int userChoice = -1;
        while (isRunning) {
            System.out.println("""
                \nSELECT WHAT YOU WANT TO DO
                [1] Display Campus Map
                [2] View All Locations and Events
                [3] Plan a Route
                [4] Search
                [5] Get Coordinates of a Location
                [6] Exit""");
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

                case 2 -> { System.out.println(); currentUser.navigate();} // shows role-filtered locations again

                case 3 -> {
                    try {
                        System.out.println("\nAvailable locations:");
                        for (CampusEntity e : campus) {
                            System.out.println(e.getId() + " - " + e.getName());
                        }
                        System.out.print("Enter starting location ID: ");
                        String startId = input.nextLine().toUpperCase();
                        System.out.print("Enter destination location ID: ");
                        String endId = input.nextLine().toUpperCase();

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
                        System.out.println("Total Distance: " + df.format(route.getTotalDistance()) + " Imaginary Meters");
                    } catch (NavigationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }


                case 4 -> {
                    System.out.println("\nSEARCH");
                    try{
                        System.out.print("Enter What to Search: "); String term = input.nextLine().toLowerCase();
                        
                        boolean isFound = false;
                        for (CampusEntity e : campus){
                            boolean nameMatch = e.getName().toLowerCase().contains(term);
                            boolean facilityMatch = false;
                            if (e instanceof Building bldg){
                                for (String facility : bldg.getFacilities()){
                                    if (facility.toLowerCase().contains(term)){
                                        facilityMatch = true;
                                        break;
                                    }
                                }
                            }
                            if (facilityMatch || nameMatch){
                                System.out.println(e.getInfo());
                                isFound = true;
                            }
                        }
                        if (!(isFound)){
                            System.out.println(term + " is Not Found!");
                        }
                    }catch(Exception e){
                        System.out.println("Error: " + Messages.msg[4]);
                    }
                }


                case 5 -> {

                    try{
                        System.out.println("\nAVAILABLE LOCATIONS");

                        for(CampusEntity e : campus){
                            System.out.println(e.getId() + " - " + e.getName());
                        }
                        System.out.print("Enter Location ID: "); String locId = input.nextLine();
                        
                        CampusEntity target = null;
                        for (CampusEntity e : campus){
                            if (e.getId().equalsIgnoreCase(locId)){
                                target = e;
                            }

                        }
                        if (target == null){
                            throw new NavigationException(Messages.msg[3]);
                        }
                        System.out.println(target.getDirections());

                    } catch (Exception e){
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 6 -> {
                    System.out.println("Exiting Program.");
                    System.out.println("Thank you!");
                    isRunning = false;
                }

                default -> System.out.println(Messages.msg[4]);
            }
        }
    }
}
