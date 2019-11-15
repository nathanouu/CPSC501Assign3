import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;


public class ObjectCreator {

    private static Scanner in = new Scanner(System.in);

    //Creates and returns an instance of a simple primitive object
    public PrimitiveObject createPrimitiveObject() {
        System.out.println("Creating Primitive Object");
        PrimitiveObject primObj = null;

        try {
            System.out.print("Setting primitive instance variables");

            //Integer primitive
            System.out.println("Enter value for integer field");
            handleInput(1);
            int intParam = in.nextInt();

            //Float
            System.out.println("Enter value of float field");
            handleInput(2);
            float floatParam = in.nextFloat();

            primObj = new PrimitiveObject(intParam, floatParam);

        }catch(Exception e ) { e.printStackTrace(); }
        
        return primObj;
    }
    
    //Creates and returns an instance of a reference object
    public ReferenceObject createReferenceObject() {
        System.out.println("Creating Reference Object");
        ReferenceObject refObj = null;

        //Creation of other objects at same time
        PrimitiveObject primObj = createPrimitiveObject();
        refObj = new ReferenceObject(primObj);

        return refObj;
    } 

    
    //Creates and returns a PrimitiveArray object 
    public PrimitiveArrayObject createPrimitiveArrayObject() {
        System.out.println("Creating PrimitiveArray Object");
        PrimitiveArrayObject primArrayObject = null;
        
        //Prompt user for array length 
        System.out.println("Enter size of array:");
        handleInput(1);
        int arrayLength = in.nextInt();

        //Allow user to set the values of the array elements
        String[] paramArray = new String[arrayLength];
        System.out.println("Press enter after every complete entry");

        for (int i = 0; i < paramArray.length; i++) {
            System.out.printf("Enter value for index %d:\n", i);
            paramArray[i] = in.nextLine();
        }

        primArrayObject = new PrimitiveArrayObject(paramArray);
        return primArrayObject;
    }  
    
    //Creates and returns a ReferenceArray Object
    public ReferenceArrayObject createReferenceArrayObject() {
        System.out.println("Creating ReferenceArray Object");
        ReferenceArrayObject refArrayObject = null;

        //Prompt user for array length 
        System.out.println("Enter size of array:");
        handleInput(1);
        int arrayLength = in.nextInt();

        Object[] paramArray = new Object[arrayLength];

        for (int i = 0; i < paramArray.length; i++) {
            //Creation of primitive objects at same time
            paramArray[i] = createPrimitiveObject();
        }

        refArrayObject = new ReferenceArrayObject(paramArray);
        return refArrayObject;

    }
    
    //Creates and returns a Collections Object
    public CollectionObject createCollectionObject() {
        System.out.println("Creating Collections Object");
        CollectionObject colObj = null;

        Vector<Object> paramCollection = new Vector<Object>();
        //Prompt user for creation of objects into collection
        boolean quit = false;
        String choice;
        while(!quit){
            System.out.println("Add object to collections yes = 1 and no = 0");
            //handleInput(3);
            choice = in.nextLine();
            
            if (choice.equals("0")) {
                quit = true;
            }
            else if (!choice.equals("0")) {
                paramCollection.add(createPrimitiveObject());
            }
            in.nextLine();
        }
        colObj = new CollectionObject(paramCollection);
        return colObj;    
    }

    public static void handleInput(int mode) {
        switch (mode) {
            case 1 : {
                while (!in.hasNextInt()) {
                    in.next();
                    System.out.println("Invalid value for int field");
                }
                break;
            }
            case 2 : {
                while (!in.hasNextFloat()) {
                    in.next();
                    System.out.println("Invalid value for float field");
                }
                break;
            }
            case 3 : {
                while (!in.hasNextInt() && ((in.nextLine().equals("0")) || (in.nextLine().equals("1")))) {
                    System.out.println("Invalid choice, 0 = don't add to collection, 1 = add to collection");
                    in.next();
                }
                break;
            }
        } 
    }
    
 }