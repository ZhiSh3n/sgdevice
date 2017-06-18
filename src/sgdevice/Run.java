package sgdevice;

import java.util.Scanner;

public class Run {
	
	public static int integerReader() {
		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int temporaryInteger = temporaryReader.nextInt();
		return temporaryInteger;
	}

	public static void main(String[] args) {
		System.out.println("How many SG devices?");
		int sgCount = integerReader();
		System.out.println("You have " + sgCount + " SG Devices.");
	}

}
