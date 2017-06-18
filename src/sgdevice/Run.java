package sgdevice;

import java.util.Scanner;

public class Run {

	public static int integerReader() {
		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int temporaryInteger = temporaryReader.nextInt();
		return temporaryInteger;
	}

	public static void orientationChooser(Holder holder) {
		
		Scanner temporaryReader = new Scanner(System.in);
		System.out.println("Choose an orientation: [z][x][other]");
		
		String zValue = new String("z");
		String xValue = new String("x");
		String otherValue = new String("other");
		
		String receive = temporaryReader.nextLine();
		String temporaryValue = new String(receive); // we have an String object [temporaryValue] we can compare to
		
		
		if ((!temporaryValue.equals(zValue) && !temporaryValue.equals(xValue)) && !temporaryValue.equals(otherValue)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(holder);
		} else {
			holder.setVal(receive);
		}
	}

	public static void main(String[] args) {
		/*
		 * System.out.println("How many SG devices?"); int SGCount =
		 * integerReader();
		 * 
		 * Device[] SGDevices = new Device[SGCount];
		 */
		System.out.println("What orientation should this SG device be?");
		
		String orientation = "initial orientation"; // initialize the orientation
		Holder one = new Holder(orientation); // made a new holder that holds the String [orientation]
		orientationChooser(one);
		System.out.println(one.getVal());
		
	}

}
