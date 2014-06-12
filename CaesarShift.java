import java.io.*;
import java.util.*;


public class CaesarShift implements Code {
	private int key;
	private String outputFileName;
	
	public CaesarShift(int shift, String outputFileName) {
		key = shift % 26;
		this.outputFileName = outputFileName;
	}
	
	public void decrypt(String inputFileName) throws FileNotFoundException{ 
		shift(inputFileName, 26-key);
	}
	
	public void encrypt(String inputFileName) throws FileNotFoundException{ 
		shift(inputFileName, key);
	}
	
	public void shift(String inputFileName, int key) throws FileNotFoundException {
		Scanner console = new Scanner(new File(inputFileName));
		String decryption = "";
		while(console.hasNext()) {
			String cipherPiece = console.next();
			String decryptPiece = "";
			for(int index = 0; index < cipherPiece.length(); index++) {
				char letter = cipherPiece.charAt(index);
				if ((letter >= 65) && (letter <= 90)) {
					if ((letter + key) <= 90 ) {
						decryptPiece = decryptPiece + (char)(letter + key);
					} else {
						decryptPiece = decryptPiece + (char)(((letter + key) - 90) + 64);
					}
				} else if ((letter >= 97) && (letter <= 122)) {
					if ((letter + key) <= 122 ) {
						decryptPiece = decryptPiece + (char)(letter + key);
					} else {
						decryptPiece = decryptPiece + (char)(((letter + key) - 122) + 96);
					}
				} else if ((letter >= 48) && (letter <= 57)) {
					if ((letter + key) <= 57 ) {
						decryptPiece = decryptPiece + (char)(letter + key);
					} else {
						decryptPiece = decryptPiece + (char)(((letter + key) - 57) + 47);
					}
				} else {
					decryptPiece = decryptPiece + (char)(letter);
				}
				
			}
			decryption = decryption + decryptPiece + " ";
		}
		console.close();
		PrintStream output = new PrintStream(new File(outputFileName));
		output.println(decryption);
		output.close();
	}
}


