import java.io.IOException;

public class Main {
	
	// Variable for command line argument
	
	public static String savedArg;
	
	// Main class

	public static void main(String[] args) throws Exception {
		
		// Getting and storing command line argument
		
		savedArg = args[0];

		FileRead a = new FileRead();
		
		// Reading and processing each file

		a.FileHandle("customer");
		a.FileHandle("input");

	}

}
