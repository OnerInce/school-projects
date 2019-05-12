import java.io.*;

public class Main {
	
	// Variable for command line argument
	
	public static String savedArg;
	
	public static void main(String[] args) throws IOException {
		
			// Getting and storing command line argument
		
			savedArg = args[0];
		
			FileRead a = new FileRead();
			
			// Reading and processing each file
			
			a.FileHandle("people");
			a.FileHandle("food");
			a.FileHandle("sport");
			a.FileHandle("input");
		}
		
		
	}

