import java.io.File; 
import java.io.FileNotFoundException; 
  
import java.util.Scanner; 
  
  
public class KeyOptional { 
    public static final char[] LETTERS = {'E','T','A','O','I','N','S','H','R','D','L','C','U','M','W','F','G','Y','P','B','V','K','J','X','Q','Z'}; 
    public static void caesarShift(String inputFileName, String outputFileName) throws Exception{ 
        for(int key = 0; key < 26; key++){ 
            CaesarShift c = new CaesarShift(key, outputFileName + key); 
            c.decrypt(inputFileName); 
            File output = new File(outputFileName + key); 
            double correct = spellCheck(output); 
            if(correct < .75) { 
                output.delete(); 
            } else { 
                System.out.println(outputFileName + key); 
                System.out.println(correct); 
            } 
        } 
    } 
      
    public static void substitution(String inputFileName, String outputFileName, Scanner console) throws FileNotFoundException { 
        // Do a frequency analysis 
        Dictionary freqs = frequencyAnalysis(inputFileName); 
        // make it into a dictionary with the corresponding letters 
        Dictionary matches = matchUp(freqs); 
        //  ask the user for input 
        boolean keepTrying = showCurrent(outputFileName, console, inputFileName, matches, freqs); 
        while (keepTrying) { 
            promptUser(matches, console); 
            keepTrying = showCurrent(outputFileName, console, inputFileName, matches, freqs); 
        } 
          
    } 
    
    // gets the frequency with which certain letters appeared
    public static Dictionary frequencyAnalysis(String inputFileName) throws FileNotFoundException{ 
        File inputFile = new File(inputFileName); 
        Scanner input = new Scanner(inputFile); 
        Dictionary frequencies = new Dictionary(); 
        String piece = ""; 
        while(input.hasNext()){ 
            piece = input.next().toLowerCase(); 
            for(int i = 0; i < piece.length(); i++){ 
                char current = piece.charAt(i); 
                if(frequencies.containsKey(current)){ 
                    // Change value of the pair with the key to be one greater 
                    frequencies.setValue(current, frequencies.getValue(current) + 1); 
                } else if ((current >= 97) && (current <= 122)){ 
                    frequencies.keys.add(new Pair(current, 1)); 
                }  
            } 
        } 
        input.close(); 
        return frequencies; 
    } 
      
    public static Dictionary matchUp(Dictionary freq){ 
        // creates a temporary dictionary 
        Dictionary temp = new Dictionary(); 
        // clones the frequency dictionary into the temporary one 
        for(Pair p : freq.keys){ 
            temp.keys.add(new Pair(p.key, p.value)); 
        } 
        // creates the new dictionary 
        Dictionary matches = new Dictionary(); 
        int tempLoc; 
        int biggest; 
        int k = 0; 
        // while there is still stuff left in the temporary dictionary 
        while(temp.keys.size() > 0){ 
            // it sets the first biggest as the first value 
            tempLoc = 0; 
            biggest = temp.keys.get(0).value; 
            for(int j = 0; j < temp.keys.size(); j++){ 
                if(temp.keys.get(j).value > biggest){ 
                    tempLoc = j; 
                    biggest = temp.keys.get(j).value; 
                } 
            } 
            matches.keys.add(new Pair(temp.keys.get(tempLoc).key, (int) LETTERS[k])); 
            temp.keys.remove(tempLoc); 
            k++; 
        } 
          
        return matches; 
    } 
    
    // shows the current interpretation of the cipher and asks if they want to change it
    public static boolean showCurrent(String outputFileName, Scanner console, String inputFileName, Dictionary matches, Dictionary freqs) throws FileNotFoundException { 
        Substitution cipher = new Substitution(matches, outputFileName); 
        cipher.decrypt(inputFileName); 
        Scanner currentOutput = new Scanner(new File(outputFileName)); 
        while(currentOutput.hasNextLine()){ 
            System.out.println(currentOutput.nextLine()); 
        } 
        currentOutput.close();
        System.out.println(cipher.getDict()); 
        System.out.println(freqs.toString("not")); 
        System.out.print("Would the user like to make any suggestions? yes or no"); 
        return console.next().equals("yes"); 
    } 
    
    // asks the user to input new keys
    public static void promptUser(Dictionary dict, Scanner console){ 
        System.out.println("Please give in the format key + '-' + value, with a ';' between them"); 
        System.out.print("Keys: ");  
        String newKeys = console.next(); 
        System.out.println(); 
        for(int i = 0; i < newKeys.length(); i += 4){ 
            dict.setValue(newKeys.charAt(i), newKeys.charAt(i + 2)); 
        }    
    } 
    
    // returns the percent of words which are in the dictionary
    public static double spellCheck(File words) throws Exception { 
        int total = 0; 
        int correct = 0; 
        Scanner file = new Scanner(words); 
        String word = ""; 
        while(file.hasNext()){ 
            boolean found = false; 
            word = file.next().toLowerCase(); 
            Scanner dict = new Scanner(new File(word.charAt(0) + "Dict.txt")); 
            while(dict.hasNext() && !found) { 
                String check = dict.next(); 
                if(word.equals(check)) { 
                    correct++; 
                    found = true; 
                } 
            } 
            dict.close(); 
            total++; 
        } 
        file.close(); 
        return (double)correct/total; 
    } 
} 
