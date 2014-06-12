import java.util.Scanner;


public class Client {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		boolean moreFiles = introduction(input);
		while(moreFiles) {
			String inputFile = getInputFile(input);
			boolean moreCiphers = true;
			while(moreCiphers) {
				moreCiphers = getInfo(input, inputFile);
			}
			moreFiles = promptFiles(input);
		}
		conclusion();
	}

	// Introduces the application and explains what can be done with it
	public static boolean introduction(Scanner input) {
		System.out.println("Hello! This is the Crazy Crypto application.");
		System.out.println("In this you have the option to encrypt or decrypt with a  known key");
		System.out.println("an inputed file into another file with the following ciphers:");
		System.out.println("CaesarShift, Vigenere, Substitution, AES, 3DES, and DES");
		System.out.println("Or decrypt without a known key this subset:");
		System.out.println("CaesarShift, Vigenere, and Substitution");
		System.out.print("Would you like to input a file? Respond yes or no");
		String answer = input.next().toLowerCase();
		System.out.println();
		return answer.charAt(0) == 'y';
	}
	
	// Gets the name of the file the person would like to input
	public static String getInputFile(Scanner input) {
		System.out.println("What is the name of the file you would like to import?");
		System.out.println("Make sure to include the complete location");
		System.out.print("File");
		String inputFile = input.next();
		System.out.println();
		return inputFile;
	}
	
	// Gets all the info that would be used in the cipher
	public static boolean getInfo(Scanner input, String inputFile) throws Exception {
		System.out.print("Is the key known? Respond yes or no");
		boolean keyknown = input.next().toLowerCase().charAt(0) == 'y';
		System.out.print("Which cipher would you like to use?");
		String cipher = input.next();
		System.out.println();
		System.out.println("Which output file would you like to use?");
		System.out.println("Make sure to include the complete location");
		System.out.print("File");
		String outputFile = input.next();
		System.out.println();
		if(!keyknown){
			if(cipher.equals("CaesarShift")) {
				KeyOptional.caesarShift(inputFile, outputFile);
			} else {
				KeyOptional.substitution(inputFile, outputFile, input);
			}
		} else {

			System.out.println();
			System.out.print("Would you like to encrypt or decrypt?");
			String method = input.next();
			System.out.println();
			if(cipher.equals("CaesarShift")){
				System.out.print("What shift would you like?");
				int shift = Integer.parseInt(input.next());
				CaesarShift c = new CaesarShift(shift, outputFile);
				if(method.equals("decrypt")){
					c.decrypt(inputFile);
				} else {
					c.encrypt(inputFile);
				}
			} else if(cipher.equals("Vigenere")){
				System.out.print("What keyword would you like?");
				String keyword = input.next();
				Vigenere c = new Vigenere(keyword, outputFile);
				if(method.equals("decrypt")){
					c.decrypt(inputFile);
				} else {
					c.encrypt(inputFile);
				}
			} else if(cipher.equals("Substitution")){
				System.out.println("Which dictionary file would you like to use?");
				System.out.println("Make sure to include the complete location");
				System.out.print("File");
				String dictionaryFile = input.next();
				Dictionary d = new Dictionary(dictionaryFile);
				Substitution c = new Substitution(d, outputFile);
				if(method.equals("decrypt")){
					c.decrypt(inputFile);
				} else {
					c.encrypt(inputFile);
				}			
			} else if(cipher.equals("AES")){
				System.out.print("What key would you like?");
				String key = input.next();
				AES c = new AES(key, outputFile);
				if(method.equals("decrypt")){
					c.decrypt(inputFile);
				} else {
					c.encrypt(inputFile);
				}	
			}else if(cipher.equals("3DES")){
				System.out.print("What key would you like?");
				String key = input.next();
				TripleDES c = new TripleDES(key, outputFile);
				if(method.equals("decrypt")){
					c.decrypt(inputFile);
				} else {
					c.encrypt(inputFile);
				}	
			} else {
				System.out.print("What key would you like?");
				String key = input.next();
				DES c = new DES(key, outputFile);
				if(method.equals("decrypt")){
					c.decrypt(inputFile);
				} else {
					c.encrypt(inputFile);
				}	
			}
		}
		System.out.print("Would you like to use another cipher? Respond yes or no");
		String answer = input.next();
		System.out.println();
		if(answer.equals("yes")){
			return true;
		} else
			return false;
	}
	
	public static boolean promptFiles(Scanner input){
		System.out.print("Would you like to input a file? Respond yes or no");
		String answer = input.next();
		System.out.println();
		return answer.equals("yes");
	}
	
	public static void conclusion() {
		System.out.println("Thank you for using this program.");
	}
}
