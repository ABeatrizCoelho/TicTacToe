package util;

import java.io.PrintStream;
import java.util.Scanner;

public class Console {
	
	static Scanner scanner = new Scanner(System.in);
	static PrintStream out = System.out;
	
	static public 
	int readInt(String message) {
		System.out.print(message + " ");
		int value = scanner.nextInt();
		scanner.nextLine();
		return value;
	}
	
	static public int readInt(String message, int min, int max) {
		final String msgFormat = "\n%s [%d-%d]";
		int value = Integer.MIN_VALUE;
		while(value < min || value > max) {
			value = readInt(String.format(msgFormat, message, min, max));
		}
		return value;
	}
	
	static public  
	float readfloat(String message) {
		System.out.print(message + " ");
		float value = scanner.nextFloat();
		scanner.nextLine();
		return value;
	}
	
	static public  
	String readLine(String message) {
		System.out.print(message + " ");
		String value = scanner.nextLine();
		return value;
	}
	
	static public char readChar(String message, char... acceptedValues) {
		String strAccepted = new String(acceptedValues);
		final String format = "\n%s";
		char value;
		do {
			out.printf(format, message);
			String line = scanner.nextLine();
			value = line.length() > 0 ? line.charAt(0) : ' ';
		} while(strAccepted.indexOf(value) < 0);
		return value;
	}
	
	static public
	void printf(String format, Object... args) {
		out.printf(format, args);
	}
	
	static public
	void println(String message) {
		out.println(message);
	}
	
	static public
	void println() {
		out.println();
	}
}