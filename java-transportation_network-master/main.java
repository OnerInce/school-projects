import java.io.*;
import java.util.*;

public class main {
	
public static String[] myArgs;

	public static void main(String[] args) {
		
		myArgs = args;
		
		// Set output stream to outout file
		
		PrintStream fileOut = null;
		try {
			fileOut = new PrintStream(args[2]);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		System.setOut(fileOut);
		
		Util myUtil = new Util();
		myUtil.FileRead("network");
		myUtil.FileRead("query");

	}

}
