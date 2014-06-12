import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.PrintStream; 
import java.util.Scanner; 
  
  
public class Substitution 
{ 
    private Dictionary key; 
    private String outputFileName; 
    
    // Constructs a new substitution cipher with the given information
    public Substitution(Dictionary dict, String outputFileName){ 
        this.key = dict; 
        this.outputFileName = outputFileName; 
    } 
    
    // decrypts the given file with the cipher
    public void decrypt(String inputFileName) throws FileNotFoundException{  
        substitute(inputFileName, "decrypt"); 
    } 
    
    // encrypts the file with the cipher
    public void encrypt(String inputFileName) throws FileNotFoundException{  
        substitute(inputFileName, "encrypt"); 
    } 
    
    // does a substitution
    public void substitute(String inputFileName, String process) throws FileNotFoundException { 
        Scanner console = new Scanner(new File(inputFileName)); 
        String decryption = ""; 
        while(console.hasNext()) { 
            String cipherPiece = console.next().toLowerCase(); 
            String decryptPiece = ""; 
            for(int index = 0; index < cipherPiece.length(); index++) { 
                char letter = (cipherPiece.charAt(index)); 
                if(process.equals("encrypt") && key.containsValue(letter)) { 
                        decryptPiece += key.getKey(letter); 
                } else if (process.equals("decrypt") && key.containsKey(letter)) { 
                        decryptPiece += key.getValue(letter); 
                } else { 
                    decryptPiece += letter; 
                } 
            } 
            decryption = decryption + decryptPiece + " "; 
        } 
        console.close(); 
        PrintStream output = new PrintStream(new File(outputFileName)); 
        output.println(decryption); 
        output.close(); 
    } 
    
    // returns the dicitonary
    public String getDict(){ 
        return key.toString("letter"); 
    } 
} 
