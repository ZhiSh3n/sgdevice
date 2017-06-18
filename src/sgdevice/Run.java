package sgdevice;

import java.util.Scanner;

public class Run {

	public static int integerReader() {
		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int temporaryInteger = temporaryReader.nextInt();
		return temporaryInteger;
	}

	public static void orientationChooser(Device device) {

		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Choose an orientation: [z][x][other]");

		String z = new String("z");
		String x = new String("x");
		String other = new String("other");

		int number;
		String orientation = temporaryReader.nextLine();
		String toCompare = new String(orientation); 
		
		if (toCompare.equals(other)) {
			// if equals other, ask for a degree
			System.out.println("Choose a degree.");
			number = temporaryReader.nextInt();
			System.out.println(number);
		}
														
														
		if ((!toCompare.equals(z) && !toCompare.equals(x)) && !toCompare.equals(other)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(holder);
		} else {
			holder.setVal(receive);
		}
	}
	

	public static void main(String[] args) {

		
		System.out.println("How many SG devices?");
		int SGCount = integerReader();
		Device[] SGDevices = new Device[SGCount]; // create an array of all the SG devices
		
		for (int i = 0; i < SGDevices.length; i++) {
			System.out.println("What orientation should this SG device be?");
			SGDevices[i];
		}
		

		
		
		/*
		 * String orientation = "initial orientation"; 
		Holder one = new Holder(orientation); // made a new holder that holds
												// the String [orientation]
		orientationChooser(one);
		System.out.println(one.getVal());
		*/

	}

}
