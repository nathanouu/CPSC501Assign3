import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;


public class ObjectCreator 
{

    private static Scanner in = new Scanner(System.in);

    //Creates simple primitive object
    public PrimitiveObject createPrimitiveObject() 
    {
        System.out.println("Creating Primitive Object\n");

        //Initialize the object to null
        PrimitiveObject primObj = null;

        try 
        {

            //Set a value of your choosing for the integer
            System.out.println("Enter value for integer field");
            int intParam = in.nextInt();

            //Set a value of your choosing for the float 
            System.out.println("Enter value of float field");
            float floatParam = in.nextFloat();

            //send these values to the PrimitiveObject class where there is just a constructor to create the  object
            primObj = new PrimitiveObject(intParam, floatParam);

        }
        catch(Exception e) 
        { 
            e.printStackTrace(); 
        }
        System.out.println("Primitive Object Created!\n\n");
       
        return primObj;
    }
    


    //Create a reference object
    public ReferenceObject createReferenceObject() 
    {
        System.out.println("Creating Reference Object");

        //Creation of a primitive object at the same time that references this object
        PrimitiveObject primObj = createPrimitiveObject();
        ReferenceObject refObj = new ReferenceObject(primObj);

        System.out.println("Reference Object Created!\n\n");

        return refObj;
    } 

    
    //Creates and returns a PrimitiveArray object 
    public PrimitiveArrayObject createPrimitiveArrayObject() {
        System.out.println("Creating PrimitiveArray Object");

        //Set intiial to null
        PrimitiveArrayObject pArrayObject = null;
        
        //Set the size if the array
        System.out.println("Enter size of array:");
        int arrayLength = in.nextInt();


        //Allow user to set the values of the array elements. Changed to arraylength +1. Not sure if that fixed my original problem
        String[] paramArray = new String[arrayLength+1];

        for (int i = 0; i < paramArray.length; i++) 
        {
            System.out.printf("Enter value for index %d:\n", i);
            paramArray[i] = in.nextLine();
        }
        System.out.println(paramArray[0]);

        pArrayObject = new PrimitiveArrayObject(paramArray);

        System.out.println("Primitive Array Object Created!\n\n");

        return pArrayObject;
    }  
    
    //Creates and returns a ReferenceArray Object
    public ReferenceArrayObject createReferenceArrayObject() {
        System.out.println("Creating ReferenceArray Object");
        ReferenceArrayObject refArrayObject = null;

        //Set the size if the array
        System.out.println("Enter size of array:");
        int arrayLength = in.nextInt();


        Object[] paramArray = new Object[arrayLength + 1];

        for (int i = 0; i < paramArray.length; i++) {
            //Creation a primitive object at each index of the array created
            paramArray[i] = createPrimitiveObject();
        }

        refArrayObject = new ReferenceArrayObject(paramArray);
        System.out.println("Reference Array Object Created!\n\n");

        return refArrayObject;

    }
    
    //Creates and returns a Collections Object
    public CollectionObject createCollectionObject() 
    {
        System.out.println("Creating Collections Object");
        CollectionObject colObj = null;

        Vector<Object> paramCollection = new Vector<Object>();
        //Prompt user for creation of objects into collection
        boolean quit = false;
        String choice;
        while(!quit){
            System.out.println("Add object to collections? Type 'yes' or 'no'");


            choice = in.nextLine();
            
            if (choice.equals("no")) 
            {
                quit = true;
            }
            else if (choice.equals("yes")) 
            {
                paramCollection.add(createPrimitiveObject());
            }
            in.nextLine();
        }
        colObj = new CollectionObject(paramCollection);
        return colObj;    
    }
    
 }