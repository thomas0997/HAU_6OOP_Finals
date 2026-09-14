// import java.util.Scanner;

// import javax.naming.NameAlreadyBoundException;
// import javax.sound.sampled.SourceDataLine;

// import Users.*;
// import Support.*;
// import Locations.*;


// public class Main {


//     static Scanner input = new Scanner(System.in);
//     public static User login() throws NavigationException{
//         System.out.println("""
// [1] Faculty
// [2] Student
// [3] Visitor
//         """);
//         System.out.print("Enter Choice:  "); int userRole = input.nextInt(); input.nextLine();
//         System.out.print("Enter User ID: "); String userId = input.nextLine();

//         switch (userRole){
//             case 1 -> {

//                 System.out.println("Enter Faculty ID: "); String facultyID = input.nextLine();
//                 System.out.print("Enter Password: "); String password = input.nextLine();
//                 Faculty facultyObject = new Faculty(userId, "Faculty", password, facultyID);

//                 if (!(facultyObject.authenticate())){
//                     throw new NameAlreadyBoundException("ERR004", Messages.msg[5]);
//                 } 
//                 return facultyObject;
//             }

//             case 2 -> {
//                 System.out.print("Enter Student ID: "); String studentID = input.nextLine();
//                 System.out.print("Enter Your Program: "); String program = input.nextLine();
//                 Student studentObject = new Student(userId, "Student", studentID, program);
//                 return studentObject;
//             }


//             case 3 -> {
//                 System.out.print("Enter Purpose of Visit: "); String purpose = input.nextLine();
//                 System.out.print("Enter What type of ID to Leave: "); String typeOfID = input.nextLine();
//                 Visitor visitorObject = new Visitor(userId, "Visitor", purpose, typeOfID);
//                 return visitorObject;
//             }
//         }
//     }

//     public static void main(String[] args) {

//     }
// }
