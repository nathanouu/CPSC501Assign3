import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class CollectionObject {

    private Vector<Object> objectCollectionField = null;

    public CollectionObject () {

    }

    public CollectionObject (Vector<Object> paramCollection) {
        objectCollectionField = paramCollection;
    }
 }