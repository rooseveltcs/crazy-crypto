import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


public class Vigenere implements Code{
	private String key;
	private String outputFileName;
	
	public Vigenere(String keyword, String outputFileName) {
		this.key = keyword.toLowerCase();
		this.outputFileName = outputFileName;
	}

	public void decrypt(String inputFileName) throws FileNotFoundException{
		shift(inputFileName, key);
	}
	
	public void encrypt(String inputFileName) throws FileNotFoundException{ 
		shift(inputFileName, key);
	}
	
	public void shift(String inputFileName, String keyword) throws FileNotFoundException {
		Scanner console = new Scanner(new File(inputFileName));
		String decryption = "";
		while(console.hasNext()) {
			String cipherPiece = console.next();
			String decryptPiece = "";
			int i = 0;
			for(int index = 0; index < cipherPiece.length(); index++) {
				char letter = cipherPiece.charAt(index);
				int key = keyword.charAt(i % keyword.length()) - 97;
				if ((letter >= 65) && (letter <= 90)) {
					if ((letter + key) <= 90 ) {
						decryptPiece = decryptPiece + (char)(letter + key);
					} else {
						decryptPiece = decryptPiece + (char)(((letter + key) - 90) + 64);
					}
					i++;
				} else if ((letter >= 97) && (letter <= 122)) {
					if ((letter + key) <= 122 ) {
						decryptPiece = decryptPiece + (char)(letter + key);
					} else {
						decryptPiece = decryptPiece + (char)(((letter + key) - 122) + 96);
					}
					i++;
				} else if ((letter >= 48) && (letter <= 57)) {
					if ((letter + key) <= 57 ) {
						decryptPiece = decryptPiece + (char)(letter + key);
					} else {
						decryptPiece = decryptPiece + (char)(((letter + key) - 57) + 47);
					}
					i++;
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