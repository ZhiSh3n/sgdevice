package sgdevice;

import java.util.Scanner;

public class Run {

	public static int integerReader() {
		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int temporaryInteger = temporaryReader.nextInt();
		return temporaryInteger;
	}

	public static void orientationChooser(String value) {
		String carryValue = value;
		
		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Choose an orientation: [z][x][other]");
		
		String zValue = new String("z");
		String xValue = new String("x");
		String otherValue = new String("other");
		
		carryValue = temporaryReader.nextLine();
		String temporaryValue = new String(carryValue);
		
		if ((!temporaryValue.equals(zValue) && !temporaryValue.equals(xValue)) && !temporaryValue.equals(otherValue)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(carryValue);
		}
		value = carryValue;
	}

	public static void main(String[] args) {
		/*
		 * System.out.println("How many SG devices?"); int SGCount =
		 * integerReader();
		 * 
		 * Device[] SGDevices = new Device[SGCount];
		 */
		System.out.println("What orientation should this SG device be?");
		String orientation = null;
		orientationChooser(orientation);
		System.out.println(orientation);
	}

}
