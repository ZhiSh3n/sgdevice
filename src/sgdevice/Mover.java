package sgdevice;

import java.util.ArrayList;
import java.util.Scanner;

public class Mover {
	
	// we give each node a layer instead of a name
	public int layer;
	// each node will have an empty 
	public ArrayList<Mover> children;
	// and each node will have a parent
	public Mover parent;
	// each node has a device as well
	public Device device;
	
	
	public Mover(int layer, Mover parent) {
		this.layer = layer;
		this.children = new ArrayList<>();
		this.parent = parent;
		this.device = new Device();
		orientationChooser(this.device);
	}
	
	public static void addChild(Mover mover) {
		String closed = new String("closed");
		String open = new String("open");
		if (mover.children.size() >= 2) {
			System.out.println("You can add no more children.");
		} else if (mover.device.orientation.equals(closed)){
			System.out.println("This device is closed.");
		} else if (mover.device.orientation.equals(open)) {
			System.out.println("This device is open.");
		} else {
			System.out.println("Adding Child " + (mover.children.size()));
			mover.children.add(new Mover(mover.layer+1, mover));
			System.out.println("Child added.");
		}
	}
	
	public static Mover sibling(Mover mover) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter sibling element number: ");
		int siblingNumber = scanner.nextInt();
		if (siblingNumber == mover.parent.children.indexOf(mover)) {
			System.out.println("You are already on that sibling/child.");
			return mover;
		} else {
			return mover.parent.children.get(siblingNumber);
		}

	}
	
	// move to the next layer, specifying a child number (0, 1, 2 ..)
	public static Mover next(Mover mover) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the child number: ");
		int childNumber = scanner.nextInt();
		if (childNumber >= mover.children.size()) {
			System.out.println("That child does not exist.");
			return mover;
		} else {
			return mover.children.get(childNumber);
		}
	}
	
	// go to parent
	public static Mover previous(Mover mover) {
		if (mover.parent == null) {
			System.out.println("You are already in the root node!");
			return mover;
		} else {
			System.out.println("You have moved from layer " + mover.layer + " to layer " + mover.parent.layer);
			return mover.parent;
		}
	}
	
	// view current node information
	public static void view(Mover mover) {
		refreshEigenvectors(mover);
		System.out.println("This node has " + mover.children.size() + " children.");
		if (mover.parent != null) {
			System.out.println("This node is element " + mover.parent.children.indexOf(mover) + " among its sibings.");
		}
		System.out.println("This node is in Layer: " + mover.layer);
		String other = new String("other");
		System.out.print("This node has an orientation: ");
		if (mover.device.orientation.equals(other)) {
			System.out.println(mover.device.degree);
		} else {
			System.out.println(mover.device.orientation);
		}
		System.out.print("Eigenvector: " + mover.device.first + " , " + mover.device.second);
	}
	
	public static void orientationChooser(Device device) {
		System.out.println("Choose an orientation for this device [z][x][eigenvector][other][closed][open]: ");
		Scanner reader = new Scanner(System.in);
		String z = new String("z");
		String x = new String("x");
		String other = new String("other");
		String closed = new String("closed");
		String eigenvector = new String("eigenvector");
		String open = new String("open");
		double degree;
		double first;
		double second;
		String orientation = reader.nextLine();
		String compare = new String(orientation);
		if (compare.equals(other)) {
			// if equals other, ask for a degree
			System.out.println("Choose a degree: ");
			degree = reader.nextDouble();
			device.setDegree(degree);
		}
		if (compare.equals(eigenvector)) {
			System.out.println("Input first component: ");
			first = reader.nextDouble();
			device.setFirst(first);
			System.out.println("Input second component: ");
			second = reader.nextDouble();
			device.setSecond(second);
		}
		if (!compare.equals(z) && !compare.equals(x) && !compare.equals(other) && !compare.equals(closed) && !compare.equals(open) && !compare.equals(eigenvector)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(device);
		} else {
			device.setOrientation(orientation);
		}
	}
	
	public static int[] getPosOrNegArray(Mover mover) {
		// did we call probability from 0 (positive) or 1 (negative)
		// create an array of positive and negatives
		int[] posOrNeg = new int[traceDevice(mover) - 1];
		for (int i = (posOrNeg.length - 1); i > -1; i--) {
			posOrNeg[i] = mover.parent.children.indexOf(mover);
			mover = mover.parent;
		}
		return posOrNeg;
	}
	
	public static void refreshEigenvectors(Mover mover) {
		if (mover.parent == null) {
			// you are a root and you have NO eigenvector
		} else {
			switch (mover.parent.device.orientation) {
			case "x":
				if (mover.parent.children.indexOf(mover) == 0) {
					mover.device.first = Math.sqrt(0.5);
					mover.device.second = Math.sqrt(0.5);
				} else {
					mover.device.first = Math.sqrt(0.5);
					mover.device.second = -1 * Math.sqrt(0.5);
				}
				break;
			case "z":
				if (mover.parent.children.indexOf(mover) == 0) {
					mover.device.first = 1;
					mover.device.second = 0;
				} else {
					mover.device.first = 0;
					mover.device.second = 1;
				}
				break;
			case "other":
				if (mover.parent.children.indexOf(mover) == 0) {
					// it is positve theta
					mover.device.first = Math.cos(0.5 * mover.device.degree * Math.PI / 180);
					mover.device.second = Math.sin(0.5 * mover.device.degree * Math.PI / 180);
				} else {
					// it is a negative theta
					mover.device.first = -1 * Math.sin((1 / 2) * mover.device.degree * Math.PI / 180);
					mover.device.second = Math.cos((1 / 2) * mover.device.degree * Math.PI / 180);
				}
				break;
			default:
			}
		}
	}

	// calculate probability
	public static double calculateSingularProbability(Mover mover) {
		// create an array for eigenvectors
		// eg. if traceDevice = 3, there are 2 complete SG devices
		double[][] array = new double[traceDevice(mover) - 1][2];
		int[] posOrNeg = getPosOrNegArray(mover);
		// first we go to the latest SG Device
		// note that counter is decreased by 1 already here.
		mover = mover.parent;
		for (int counter = traceDevice(mover) - 1; counter > -1; counter--){ 
			//System.out.println("Counter is: " + counter);
			switch (mover.device.orientation) {
			case "x":
				if (posOrNeg[counter] == 0) {
					array[counter][0] = Math.sqrt(0.5);
					array[counter][1] = Math.sqrt(0.5);
				} else {
					array[counter][0] = Math.sqrt(0.5);
					array[counter][1] = -1 * Math.sqrt(0.5);
				}
				break;
			case "z":
				if (posOrNeg[counter] == 0) {
					array[counter][0] = 1;
					array[counter][1] = 0;
				} else {
					array[counter][0] = 0;
					array[counter][1] = 1;
				}
				break;
			case "other":
				if (posOrNeg[counter] == 0) {
					// it is positve theta
					array[counter][0] = Math.cos(0.5 * mover.device.degree * Math.PI / 180);
					array[counter][1] = Math.sin(0.5 * mover.device.degree * Math.PI / 180);
				} else {
					// it is a negative theta
					array[counter][0] = -1 * Math.sin((1 / 2) * mover.device.degree * Math.PI / 180);
					array[counter][1] = Math.cos((1 / 2) * mover.device.degree * Math.PI / 180);
				}
				break;
			case "eigenvector":
				array[counter][0] = mover.device.first;
				array[counter][1] = mover.device.second;
				break;
			default:
			}
			mover = mover.parent;
		}

		int remadeArrayLength = array.length * 2 - 2;
		double[][] remadeArray = new double[remadeArrayLength][2];
		remadeArray[0][0] = array[0][0];
		remadeArray[0][1] = array[0][1];
		remadeArray[remadeArray.length - 1][0] = array[array.length - 1][0];
		remadeArray[remadeArray.length - 1][1] = array[array.length - 1][1];
		
		for(int a = array.length - 2; a > 0; a--) { // start at second to last element of array
			remadeArray[(2*a)][0] = array[a][0];
			remadeArray[(2*a)][1] = array[a][1];
			
			remadeArray[(2*a)-1][0] = array[a][0];
			remadeArray[(2*a)-1][1] = array[a][1];
		}
		
		// a, b, c
		// a, b, b, c
		
		// a, b, c, d
		// a, b, b, c, c, d, 
		
		// cross 
		
		System.out.println("Individual Eigenvectors:"); // note that eigenvectors are printed in backwards form from the way devices are placed
		System.out.println("");
		for (int a = array.length - 1; a > -1; a--) {
			for (int b = 0; b < array[0].length; b++) {
				System.out.println(array[a][b]);
			}
			System.out.println("-------");
		}
		System.out.println("");
		System.out.println("-------");
		System.out.println("");
		System.out.println("Eigenvector calculation preparation:");
		System.out.println("");
		
		
		for (int a = remadeArray.length - 1; a > -1; a--) {
			for (int b = 0; b < remadeArray[0].length; b++) {
				System.out.println(remadeArray[a][b]);
			}
			System.out.println("-------");
		}
		
		
		System.out.println("");
		System.out.println("-------");
		System.out.println("");
		System.out.println("Eigenvector pairs are now cross multiplied: ");
		System.out.println("");
		
		// now i need to make another array to store values that later i will multiply
		double[] finalArray = new double[remadeArray.length / 2];
		
		for(int a = remadeArray.length -1; a > -1; a = a - 2) {
			double result = 1;
			result = (remadeArray[a][0] * remadeArray[a-1][0]) + (remadeArray[a][1] * remadeArray[a-1][1]);
			finalArray[((a+1)/2) - 1] = result;
		}
		
		// then we multiply the finalArray values togetehr, then square for probability
		double toPrint = 1;
		for(int a = finalArray.length - 1; a > -1; a--) {
			toPrint *= finalArray[a];
		}
		
		toPrint = Math.pow(toPrint, 2);
		
		/*
		double result = 1;
		double resultresult = 0;
		for (int b = 0; b < array[0].length; b++) {
			for (int a = array.length - 1; a > -1; a--) {
				System.out.println(array[a][b]);
				result *= array[a][b];
			}
			resultresult = resultresult + result;
		}
		*/
		//resultresult = Math.pow(resultresult, 2);
		//return resultresult;
		
		return toPrint;
	}

	// how many devices until root?
	public static int traceDevice(Mover mover) {
		int counter = 0;
		while (mover.parent != null) {
			mover = mover.parent;
			counter++;
		}
		return (counter + 1);
	}
	

}
