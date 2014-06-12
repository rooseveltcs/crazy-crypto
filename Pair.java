public class Pair { 
    char key; 
    int value; 
      
    public Pair(char key, int value){ 
        this.key = key; 
        this.value = value; 
    } 
    
    // returns a string of a pair, with the value specified as either a letter or otherwise
    public String toString(String type){ 
        if(type.equals("letter")){ 
            return key + "-" + (char)value; 
        } else { 
            return key + "-" + value; 
        } 
    } 
}