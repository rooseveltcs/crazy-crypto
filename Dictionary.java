import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.ArrayList; 
import java.util.Scanner; 
  
  
public class Dictionary { 
    ArrayList<Pair> keys; 
      
    // Creates a new empty Dictionary 
    public Dictionary() { 
        keys = new ArrayList<Pair>(); 
    } 
      
    // Creates a dictionary by reading a file with pairs of letters separated by a '-' 
    public Dictionary(String filename) throws FileNotFoundException{ 
        keys = new ArrayList<Pair>(); 
        Scanner console = new Scanner(new File(filename)); 
        while(console.hasNext()){ 
            String part = console.next(); 
            // creates a new pair and adds it to the list, first value is key, second is value 
            keys.add(new Pair(part.charAt(0), (int)part.charAt(2))); 
        } 
        console.close(); 
    } 
      
    // gets the key of the pair based on the value 
    public char getKey(char value) { 
        char k = value; 
        for(Pair p : keys) { 
            if((char)p.value == value){ 
                k = p.key; 
            } 
        } 
        return k; 
    } 
      
    // gets the value of the pair based on the key 
    public char getValue(char key){ 
        char v = key; 
        for(Pair p : keys) { 
            if(p.key == key){ 
                v = (char)p.value; 
            } 
        } 
        return v; 
    } 
    
    // gets the location of a certain key
    public int getLocation(char key){ 
         int i = 0; 
         for(Pair p : keys){ 
             if(p.key == key){ 
                 return i; 
             } else { 
                 i++; 
             } 
         } 
         return i; 
    } 
      
    // checks if the dictionary contains a key 
    public boolean containsKey(char key){ 
        boolean contains = false; 
        for(Pair p : keys) { 
            if(p.key == key){ 
                contains = true; 
            } 
        } 
        return contains; 
    } 
      
    // checks if the dictionary contains a value 
    public boolean containsValue(char value){ 
        boolean contains = false; 
        for(Pair p : keys) { 
            if(p.value == value){ 
                contains = true; 
            } 
        } 
        return contains; 
    } 
      
    public String toString(String type){ 
        String combined = ""; 
        for(Pair p : keys) { 
            combined += p.toString(type) + " "; 
        } 
        return combined; 
    } 
      
    public void setValue(char key, int value){ 
        for(Pair p : keys) { 
            if(p.key == key){ 
                p.value = value; 
            } 
        } 
    } 
} 