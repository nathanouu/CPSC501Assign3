/*
Assignment 3 Sender.java
Nathan Ou 10079578
*/ 





import java.lang.reflect.*;
import org.jdom2.*;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.net.*;

 public class Sender {

    private static ArrayList<Object> objectList = new ArrayList<>();

    public static void main (String[] args) {

        ObjectCreator objCreator = new ObjectCreator();
        ObjectSerializer serializer = null;
        Scanner in = new Scanner(System.in);

        Object obj = null;
        String objects = null;


        
        System.out.println("Please select the option(s) of the object you want to create separated by a space: " + System.out.println() + 
        "A. A simple object with primitives for instance variables" + System.out.println() +
        "B. An object that contains references to another object" + System.out.println() +
        "C. An object that contains an array of primitives" + System.out.println() +
        "D. An object that contains an array of object references" + System.out.println() +
        "E. An object that that usesone of Java's collections" + System.out.println());

        objects = in.nextLine();

        //Further development. Create a class that creates objects. Create the options the user selects
        String[] objectArray = objects.split(" ");

        for (String object : objectArray) {
            switch (object) {
                case "A" : {
                    objectList.add(objCreator.createPrimitiveObject());
                    break;
                }
                case "B" : {
                    objectList.add(objCreator.createReferenceObject());
                    break;
                }
                case "C" : {
                    objectList.add(objCreator.createPrimitiveArrayObject());
                    break;
                }
                case "D" : {
                    objectList.add(objCreator.createReferenceArrayObject());
                    break;
                }
                case "E" : {
                    objectList.add(objCreator.createCollectionObject());    
                    break;
                    }
                default :
                    System.out.println("Invalid format. Please enter one or more choices from A-D");
                    break;
            }
        }

    }
}


