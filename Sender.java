/*
Assignment 3 Sender.java
Nathan Ou 10079578

References: 

1. https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
2. Some code adapted from https://github.com/jpchung
3. Additional troubles help/troubleshooting from my tutor, Brian Cheung


*/ 





import java.lang.reflect.*;
import org.jdom2.*;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.net.*;

 public class Sender {

    private static ArrayList<Object> objectList = new ArrayList<>();

    public static void main (String[] args) 
    {

        ObjectCreator objCreator = new ObjectCreator();
        ObjectSerializer serializer = null;
        Scanner in = new Scanner(System.in);

        Object obj = null;
        String objects = null;


        
        System.out.println("Please select the option(s) of the object you want to create separated by a space: \n" +
        "A. A simple object with primitives for instance variables\n" +
        "B. An object that contains references to another object\n" +
        "C. An object that contains an array of primitives\n" +
        "D. An object that contains an array of object references\n" +
        "E. An object that that uses one of Java's collections\n");

        objects = in.nextLine();

        //Further development. Create a class that creates objects. Create the options the user selects
        String[] objectArray = objects.split(" ");

        for (String object : objectArray) 
        {
            //Create object depending on what user entered
            switch (object) 
            {
                case "A" : 
                {
                    objectList.add(objCreator.createPrimitiveObject());
                    break;
                }
                case "B" : 
                {
                    objectList.add(objCreator.createReferenceObject());
                    break;
                }
                case "C" : 
                {
                    objectList.add(objCreator.createPrimitiveArrayObject());
                    break;
                }
                case "D" : 
                {
                    objectList.add(objCreator.createReferenceArrayObject());
                    break;
                }
                case "E" : 
                {
                    objectList.add(objCreator.createCollectionObject());    
                    break;
                }
                default :
                    System.out.println("Invalid format. Please enter one or more choices from A-E");
                    break;
            }

 

        }
            System.out.println("Here are the objects you have created: \n");
            System.out.println(objectList.toString());

            System.out.println("Type 'Y' to serialize or 'N' to exit.");
            String s = in.nextLine();

            if (s.equals("Y") or s.equals("y"))
            {
                //Serialize objects 
                serializer = new ObjectSerializer(objectList);
                //Send objects
                sendFile("localhost", 8000, serializer.getSerializedFile());
            }
            
            else if (s.equals("N") or s.equals("n"))
            {
                System.exit(0);
            }

    }


    //Send the file over to the reciever over the designated host and port number
    //https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
    private static void sendFile(String host, int port, File file){
        try
        {
            System.out.println("Connecting to " + host + " on port: " + port);

            //Create a socket connection at host and port given above when calling sendfile()
            Socket socket = new Socket(host, port);
            System.out.println("Sender connected to " + socket.getRemoteSocketAddress());


            //Create streams to read and write from
            OutputStream outputStream = socket.getOutputStream();

            FileInputStream fileInputStream = new FileInputStream(file);


            long length = file.length();
            byte[] fileBytes = new byte[(int) length];
            int bytesRead = 0;
            //keep writing until there are still unread/unwritten bytes
            while((bytesRead = fileInputStream.read(fileBytes)) > 0)
            {
                outputStream.write(fileBytes, 0, bytesRead);
            }

            //close all streams and your socket
            fileInputStream.close();
            outputStream.close();
            socket.close();

            System.out.println("File sent!");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}


