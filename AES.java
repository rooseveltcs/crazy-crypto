import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AES implements Code{
	
	private SecretKey key;
	private String outputFileName;
	int paddingLength;
	
	// Creates a Secret Key by changing the plaintext key to hex and padding it if it is too short
	public AES(String key, String outputFileName) throws UnsupportedEncodingException{
		key = pad(toHexString(key));
		this.key = new SecretKeySpec(DatatypeConverter.parseHexBinary(key), "AES");
		this.outputFileName = outputFileName;
	}

	// it decrypts the file
	public void decrypt(String inputFileName) throws Exception{
		performCipher(inputFileName,  "decrypt");
		
	}
	
	// it encrypts the file
	public void encrypt(String inputFileName) throws Exception{ 
		performCipher(inputFileName, "encrypt");
	}
	
	// performs the cipher on it
	public void performCipher(String inputFileName, String type) throws Exception{
		Scanner console = new Scanner(new File(inputFileName));
		String finalPart = "";
		// changes everything to hex and adds it to the final part (if it is encrypting, decrypting assumes already in hex)
		while(console.hasNextLine()) {
			if (type.equals("encrypt")){
				finalPart += toHexString(console.nextLine());
			} else {
				finalPart += console.nextLine();
			}
		}
		// pads the hex if too short (again decryption assumes this has already been done) and records the amount
		if (type.equals("encrypt")){
			paddingLength = 32 - (finalPart.length() % 32);
			finalPart = pad(finalPart);
		}
		try {
		    Cipher cipher;
		    cipher = Cipher.getInstance("AES/ECB/NoPadding");
		    if(type.equals("decrypt")){
		    	cipher.init(Cipher.DECRYPT_MODE, key);
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			}
		    byte[] result = cipher.doFinal(DatatypeConverter.parseHexBinary(finalPart));
			finalPart = DatatypeConverter.printHexBinary(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		console.close();
		PrintStream output = new PrintStream(new File(outputFileName));
		if(type.equals("decrypt")){
			output.println(fromHexString(finalPart.substring(0, finalPart.length() - paddingLength)));
		} else {
			output.println(finalPart);
			System.out.println("Padding length = " + paddingLength);
		}
		output.close();
	}
	
	// Changes a string to hex
	public static String toHexString(String string) throws UnsupportedEncodingException {
		byte[] ba = string.getBytes("UTF-8");
	    StringBuilder str = new StringBuilder();
	    for(int i = 0; i < ba.length; i++)
	        str.append(String.format("%02x", ba[i]));
	    return str.toString();
	}
	
	// Changes hex to a string
	public static String fromHexString(String hex) {
	    StringBuilder str = new StringBuilder();
	    for (int i = 0; i < hex.length(); i+=2) {
	        str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
	    }
	    return str.toString();
	}
	
	// pads an item if it is shorter than 32 bytes, or even if it is perfect
	public static String pad(String piece){
		String more = piece;
		if(piece.length() % 32 != 0) {
			for(int i = 0; i <  32 - (piece.length() % 32); i++){
				more += "0";
			}
		}
		return more;
	}
}

