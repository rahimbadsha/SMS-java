/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentinformation;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Abdur Rahim
 */
public class StudentInformation {
    
    public static int studentsID = 0;
    public long phone;
    
    public String name = "";
    public String email = "";
    public String address = "";
    public String institute = "";
    
    public void addStudentsInfo(int sID, String sname, long sphone, 
            String semail, String saddress, String sinstitute)
    {
        try
        {
            String checkStudentsFile;
            int index;
            
            File file = new File("StudentsInfo.txt"); 
            if(!file.exists()) 
            {
                //this file will generated to the program file if there is no file yet.
                file.createNewFile();
            }
            
            RandomAccessFile studentFile = new RandomAccessFile(file, "rw");
            
            //This while loop is for to checking if there have any matching ID to the file
            //If there is matching ID will increase the ID. As we will generated the ID auto. 
            while(studentFile.getFilePointer() < studentFile.length())
            {
                checkStudentsFile = studentFile.readLine();
                
                int indexCheck = checkStudentsFile.indexOf('-'); // Find the ID. we will separate the info using this -- char
                index = Integer.parseInt(checkStudentsFile.substring(0, indexCheck));
                
                if(index == sID || index > sID) // if id is match that auto generated then increase the ID
                {
                    sID = index;
                    sID++;
                }
            }
  
            //Here we are storing the value of student given by user
            // -- is added for separating the id, name, email and so on. 
            checkStudentsFile = String.valueOf(sID) + "--" 
                + sname + "--"
                + Long.toString(sphone) + "--"
                + semail + "--"
                + saddress + "--"
                + sinstitute;

            studentFile.writeBytes(checkStudentsFile);

            studentFile.writeBytes(System.lineSeparator());

            System.out.println("\nStudent Added successfully!\n");

            studentFile.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    
    public void findStudents(int sID)
    {
        try { 
  
            String checkStudentsFile;
            int index;
            int indexCheck;
   
            File file = new File("StudentsInfo.txt"); 
  
            if (!file.exists()) 
            { 
                file.createNewFile(); 
            }
  
            RandomAccessFile studentFile = new RandomAccessFile(file, "rw");
            boolean found = false;
            
            while (studentFile.getFilePointer() < studentFile.length()) { 
  
                checkStudentsFile = studentFile.readLine();
                
                indexCheck = checkStudentsFile.indexOf('-');
                index = Integer.parseInt(checkStudentsFile.substring(0, indexCheck));
                
                if(index == sID)
                { 
                    found = true;
                    String[] student = checkStudentsFile.split("--"); 
                    
//                    for(String std: student)
//                    {
//                        System.out.println(std);
//                    }
                   
                    System.out.println("\nStudent Information:");
                    System.out.println("--------------------");
                    System.out.println("ID: " + student[0]);
                    System.out.println("Name: " + student[1]);
                    System.out.println("Phone: " + student[2]);
                    System.out.println("Email: " + student[3]);
                    System.out.println("Address: " + student[4]);
                    System.out.println("Contact: " + student[5]);
                    System.out.println();
                    
                    break;
                }
            }
            if(found == false)
            {
                System.out.println("\nStudent not found! Give valid ID.\n");
            }
        }
        catch(Exception e)
            {
                System.out.println(e);
            } 
        } 
    
    public void deleteStudent(int sID)
    {
        try { 
  
            String checkStudentsFile;
            int index;
            int indexCheck;
   
            File file = new File("StudentsInfo.txt"); 
  
            if (!file.exists()) 
            { 
                file.createNewFile(); 
            }
  
            RandomAccessFile studentFile = new RandomAccessFile(file, "rw");
            boolean found = false;
            
            while (studentFile.getFilePointer() < studentFile.length()) { // We will check the file of the last byte
  
                checkStudentsFile = studentFile.readLine(); // This will read one line from our file
                
                indexCheck = checkStudentsFile.indexOf('-');
                index = Integer.parseInt(checkStudentsFile.substring(0, indexCheck));
                
                if(index == sID)
                { 
                   found = true;  
                   break;
                }
            }
           
            if(found == true) // If given ID is found we will create new temp file and 
            {                 //copy all of the info to teh temp file except the given ID by user
                File tmpFile = new File("temp.txt");
                if(!tmpFile.exists())
                {
                    tmpFile.createNewFile();
                }
                
                RandomAccessFile tmpStudents = new RandomAccessFile(tmpFile, "rw");
                
                studentFile.seek(0); // set the file pointer to start byte of the file
                
                while(studentFile.getFilePointer() < studentFile.length())
                {
                    checkStudentsFile = studentFile.readLine();
                    
                    indexCheck = checkStudentsFile.indexOf('-');
                    index = Integer.parseInt(checkStudentsFile.substring(0, indexCheck));
                    
                    if(index == sID)
                    {
                        continue; //We will delete this data from file. So skip this to adding into new file
                       
                    }
                    
                    tmpStudents.writeBytes(checkStudentsFile);
                    tmpStudents.writeBytes(System.lineSeparator());
                }
                
                studentFile.seek(0);
                tmpStudents.seek(0);
                
                while(tmpStudents.getFilePointer() < tmpStudents.length())
                {
                    // Now copy the student info again in our main file from temp file. 
                    studentFile.writeBytes(tmpStudents.readLine());
                    studentFile.writeBytes(System.lineSeparator());
                }
                
                studentFile.setLength(tmpStudents.length());
                
                tmpStudents.close();
                studentFile.close();
                
                tmpFile.delete(); // This line will delete our temp file so user will not see it
                
                System.out.println("Student Deleted!\n");
            }
            else // If the given ID not found we will show this message to the user
            {
                studentFile.close();
                System.out.println("Student ID not Found!\n");
            }            
        }
        catch(Exception e)
            {
                System.out.println(e);
            } 
        } 
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //variable and input
        int options;
        Scanner input = new Scanner(System.in);
        
        // create an object
        StudentInformation student = new StudentInformation();
        
        while(true)
        {
             //Some Usefull instructions while run the instructions
            System.out.println("Please choose an option: \n");
            System.out.println("---------------------------------");
            System.out.println("Enter 1 for add students information:-");
            System.out.println("Enter 2 for Search Student by ID:-");
            System.out.println("Enter 3 for delete a Student by ID:-");
            System.out.println("Enter 0 to Exit the program:-");
            System.out.println("---------------------------------\n");
            
            options = input.nextInt();
            
            switch(options)
            {
                case 1:
                    System.out.print("How many students you want to add?: ");
                    int number = input.nextInt();
                    input.nextLine();
                    while(number > 0)
                    {
                        System.out.print("Enter Student name: ");
                        student.name = input.nextLine();
                        System.out.print("Enter Student phone: ");
                        student.phone = input.nextLong();
                        System.out.print("Enter Student email: ");
                        input.nextLine();
                        student.email = input.nextLine();
                        System.out.print("Enter Student address: ");
                        student.address = input.nextLine();
                        System.out.print("Enter Student institute: ");
                        student.institute = input.nextLine();
                        studentsID++;

                        student.addStudentsInfo(studentsID, student.name, student.phone,
                                student.email, student.address, student.institute);
                        number--;
                    }
                    
                    break;
                 case 2:
                    System.out.print("Enter the ID(only Integer): ");
                    int searchID = input.nextInt();
                    student.findStudents(searchID);
                    break;
                case 3:
                    System.out.print("Enter the ID to delete(Only Integer): ");
                    int deleteID = input.nextInt();
                    student.deleteStudent(deleteID);
                    break;
                case 0:
                    System.out.println("The Program is terminated!");
                    System.exit(0);
                default:
                    System.out.println("Please enter valid number. Try again!\n");
                    break;
            }
        }
    }
    
}
