package sgdevice;

import java.util.ArrayList;
import java.util.Scanner;

public class Run {

	public static void orientationChooser(Device device) {

		Scanner reader = new Scanner(System.in);

		String z = new String("z");
		String x = new String("x");
		String other = new String("other");

		int degree;
		String orientation = reader.nextLine();
		String compare = new String(orientation);

		if (compare.equals(other)) {
			// if equals other, ask for a degree
			System.out.println("Choose a degree: ");
			degree = reader.nextInt();
			device.setDegree(degree);
		}

		if ((!compare.equals(z) && !compare.equals(x)) && !compare.equals(other)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(device);
		} else {
			device.setOrientation(orientation);
		}
	}

	public static void main(String[] args) {

		/*
		System.out.println("How many SG Devices for this simulation?");
		Scanner reader = new Scanner(System.in);
		int SGCount = reader.nextInt();
		Device[] list = new Device[SGCount];

		for (int i = 0; i < list.length; i++) {
			System.out.println("Choose an orientation [z][x][other] for SG Device #" + (i + 1));
			list[i] = new Device(); // instantiate each object
			orientationChooser(list[i]);
		}

		for (int i = 0; i < list.length; i++) {
			String other = new String("other");
			if (list[i].getOrientation().equals(other)) {
				System.out.println("Orientation #" + (i + 1) + ": " + list[i].getDegree());
			} else {
				System.out.println("Orientation #" + (i + 1) + ": " + list[i].getOrientation());
			}
		}
		*/
		
		MyTreeNode<Device> root = new MyTreeNode<Device>("Hello", 90);
		System.out.println(root.getData());
		
	}
}
