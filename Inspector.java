/** 
CPSC 501 Assignment 2
Nathan Ou 10079578

References:

    1. Editted code, concepts and ideas from Jordan Kidney on GitHub
    2. https://www.geeksforgeeks.org/method-class-getname-method-in-java/
    3. https://www.geeksforgeeks.org/reflection-in-java/
    4. https://asgteach.com/2012/11/navigating-class-hierarchies-with-java-reflection/
**/



import java.lang.reflect.*;
import java.util.*;



public class Inspector
{
    public void inspect(Object obj, boolean recursive)
    {
        Class objectClass = obj.getClass();         // Get class object
        String declaringClass = objectClass.getName();      // get the declarig class based off class object above
        System.out.println("    Declaring Class: " + declaringClass);   // print declaring class


        String superClass = objectClass.getSuperclass().getName();  // get the immediate superclass
        System.out.println("    Super Class: " + superClass);   // print immediate superclass


        interfaceNames(obj, objectClass, recursive);    // call the interfaceNames method (what this does is described in comments below)
        System.out.println();


        System.out.println("\n******* Constructors");   // get constructors by calling constructorNames
        constructorNames(objectClass);


        System.out.println("\n******* Methods");    // get methods by calling methhodNames
        methodNames(objectClass);

        System.out.println("\n******* Fields"); //get fields by calling fieldNames
        fieldNames(obj, objectClass, recursive);

        System.out.println("\n******* Super Classes");  //get super classes by calling superNames
        superNames(obj, objectClass, recursive);
    }

    public void interfaceNames(Object obj, Class objectClass, boolean recursive)
    {
        Class[] interfaces = objectClass.getInterfaces();      // store interfaces in an array to keep track
        
        for (Class interFace : interfaces)     //iterate through array above
        {

            System.out.println("    Interface : " + interFace.getName());   // print out all the interfaces in the array

            if (interFace.getConstructors().length != (new Constructor[] {}).length)    // if array has elements, get the constructor of the interface
            {
                System.out.println("\n*****  Interface constructors "); 
                constructorNames(interFace);
            }

            if (interFace.getMethods().length != (new Method[] {}).length)  // if array has elements, get the methods of the interface
            {
                System.out.println("\n*****  Interface methods ");
                methodNames(interFace);
            }

            if (interFace.getConstructors().length != (new Constructor[] {}).length)    // if array has elements, get the fields of the interface
            {
                System.out.println("\n*****  Interface fields ");
                fieldNames(obj, interFace, recursive);
            }

            if (interFace.getInterfaces().length != (new Method[] {}).length)       // recursively search through 
            {
                System.out.println("\n***** Getting Recursive Interfaces");
            }

            while (interFace.getInterfaces().length != (new Method[] {}).length)    
            {
                for (Class recInterface : interFace.getInterfaces()) {
                    interfaceNames(obj, recInterface, recursive);
                }
            }
            
            System.out.println("\n****** End of Interface traversal  ******\n");
        }

    }


    public void constructorNames(Class objectClass)     
    {
        Constructor[] constructors = objectClass.getConstructors();     // create array to hold and store contructors 
        Class[] params;

        for (Constructor constructor : constructors)    // search through the array
        {
            System.out.println("    Constructor: " + constructor.getName());    // print out contructors
            
            params = constructor.getParameterTypes();
            for (Class param : params) 
            {
                System.out.println("        Parameter: " + param.getName());    // print parameter types
            }

            System.out.println("        Modifiers : " + constructor.getModifiers() + "\n"); // print modifiers 

        }

    }

    public void methodNames(Class objectClass) 
    {
        Method[] declaredMethods = objectClass.getDeclaredMethods();    // create an array that will store all of the methods

        Class[] exceptions;
        Class[] params;

        for (Method method : declaredMethods)   // search through the array and print out methods
        {
            System.out.println("    Method: " + method.getName());

            exceptions = method.getExceptionTypes();

            for (Class exception : exceptions)  // get and print exceptions
            {
                System.out.println("        Exception : " + exception.getName());
            }

            params = method.getParameterTypes();    // get and print parameters called 
            for (Class param : params) 
            {
                System.out.println("        Parameter : " + param.getName());
            }

            System.out.println("        Return type : " + method.getReturnType().getName());   // get and print return type 
            System.out.println("        Modifiers : " + method.getModifiers() + "\n");     // get and print modifiers        
            System.out.println();
        }
    }
    public void fieldNames(Object obj, Class objectClass, boolean recursive) 
    {
        Field[] declaredFields = objectClass.getDeclaredFields();   // create array that stores all the fields the class declares 

        try {

            for (Field field : declaredFields)  // go through array
            {
                System.out.println("        Field: " + field.getName());       // print field name
                System.out.println("        Type: " + field.getType().getName());   // get and print field type
                System.out.println("        Modifier: " + field.getModifiers());    // get and print modifiers

                field.setAccessible(true);
                System.out.println("        Value: " + field.get(obj) + "\n");  // get and print value of field
            }

            if (recursive)  // the recursive flag is set for the program to do recursive inspection
            {
                System.out.println("**** Recursion through fields");
                Vector<Field> objectsToInspect = new Vector();

                for (Field field : declaredFields) {
                    if(! field.getType().isPrimitive() ) 
                        objectsToInspect.addElement( field );
                }

                for (Field field : objectsToInspect) {
                    inspect(field.get(obj), recursive);
                }
            }
        }
        catch (Exception e) { System.out.println(e.getMessage()); }

    }
    public void superNames(Object obj, Class objectClass, boolean recursive)
    {
        System.out.println("\n***** Super Classes");
        methodNames(objectClass.getSuperclass());
        constructorNames(objectClass.getSuperclass());
        fieldNames(obj, objectClass, recursive);
        Class currentSuperClass = objectClass.getSuperclass();

        if (currentSuperClass.getSuperclass() != null)
            System.out.println("\n****** Getting Recursive Superclasses");

        while (currentSuperClass.getSuperclass() != null) {
            Class nextSuperClass = currentSuperClass.getSuperclass();
            System.out.println("\n******" +  nextSuperClass.getName());
            methodNames(nextSuperClass);
            constructorNames(nextSuperClass);
            fieldNames(obj, nextSuperClass, recursive);
            currentSuperClass = nextSuperClass;
        }

        System.out.println("\n******   End of super class traversal    *****\n");
    }

}











