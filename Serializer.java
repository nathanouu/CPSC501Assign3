import java.util.IdentityHashMap;

import org.jdom2.Element;
import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;


public class Serializer {

    private static IdentityHashMap objMap = new IdentityHashMap<>();

    public static Document serialize(Object obj) {

        //Initialization of root element 
        Element root = new Element("serialized");
        Document doc = new Document(root);

        return serializeObject(obj, doc);
    }

    private static Document serializeObject(Object obj, Document doc) {
         
        //declaring Different types of elements 
        Element objElement;
        Element field;

        Class objClass = obj.getClass();

        try {
            //Apply id to each object
            String objID = Integer.toString(objMap.size()); 
            objMap.put(objID, obj);

            //Create nested element with specified id and class attributes
            objElement = nestElement(objClass, objID);
            doc.getRootElement().addContent(objElement);


            //Serialize fields
            System.out.println("Serializing fields");
            objElement = handleFieldElement(objElement, obj, doc);


        } catch (Exception e) {e.printStackTrace();}
        return doc;
    }


    private static Element serializeField(Field f, Document doc, Object obj, Object fobj) {

        Element fieldElement;
        Element value;
        Element reference;

        if(fobj != null){
            fieldElement = new Element("field");

            //Set unique identifiers
            try{
             
                fieldElement.setAttribute("name", f.getName());
                fieldElement.setAttribute("declaringclass", f.getDeclaringClass().getName());

                value = new Element("value");
                reference = new Element("reference");

                Class fieldType = f.getType();

                //Check for any needed recurrsion
                if(!fieldType.isPrimitive()){
                    String fieldObjID = Integer.toString(objMap.size());
                    reference.addContent(fieldObjID);
                    fieldElement.setContent(reference);
                    serializeObject(f.get(obj), doc);

                }
                else{
                    //field is primitive/wrapper, just store value as content
                    String fieldValue = f.get(obj).toString();
                    value.addContent(fieldValue);
                    fieldElement.setContent(value);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else 
        {
            fieldElement = new Element("null");
        }

        return fieldElement;
    }
    

    private static Element nestElement(Class objClass, String objID ) 
    {
        Element objElement = new Element("object");
        objElement.setAttribute("class", objClass.getName());
        objElement.setAttribute("id", objID);
        return objElement;
    }

    private static Element handleFieldElement(Element objElement, Object obj, Document doc) {
        Element fieldElement;
        //Get all the fields
        Field[] objFields = obj.getClass().getDeclaredFields();

        //Serialize each field
        for (Field f : objFields) {
            try {
                if(!Modifier.isPublic(f.getModifiers())){
                    f.setAccessible(true);
                }
                Object fieldObj = f.get(obj);
                fieldElement = serializeField(f, doc, obj, fieldObj);
                //System.out.println(f.getName());

                objElement.addContent(fieldElement);
            }catch (Exception e) {e.printStackTrace();}
        }
        return objElement;
    }


}