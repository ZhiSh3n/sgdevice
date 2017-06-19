package sgdevice;

import java.util.Scanner;

public class Run {

	/*
	 * Define eigenvectors:
	 * 
	 * +z = [1,0] 
	 * -z = [0,1]
	 * 
	 * +x = [sqrt(1/2),sqrt(1/2)] 
	 * -x = [sqrt(1/2),-sqrt(1/2)]
	 * 
	 * +theta = [cos((1/2)theta),sin((1/2)theta)] 
	 * -theta = [-sin((1/2)theta),cos((1/2)theta)]
	 */

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Mover root = new Mover(0, null);
		Mover currentMover = root;

		/*
		 * COMMANDS TO USE currentMover = next(currentMover);
		 * currentMover.addChild(); currentMover = previous(currentMover);
		 * view(currentMover);
		 */

		while (true) {
			scanner.nextLine();
			for (int i = 0; i < 10; i++) {
				System.out.println("");
			}
			view(currentMover);
			System.out.println("");
			System.out.println("Commands: ");
			System.out.println("   [calculate] : calculate eigenvectors for particular result");
			System.out.println("   [goto child] : navigate to child number # (starting from 0)");
			System.out.println("   [goto parent] : navigate to the parent");
			System.out.println("   [goto sibling] : navigate to a sibling #");
			System.out.println("   [add child] : add a child (you will be promped to define an SG Device");
			System.out.println("   [trace device] : how many devices in a row");

			String line = scanner.nextLine();
			for (int i = 0; i < 10; i++) {
				System.out.println("");
			}

			switch (line) {
			case "calculate":
				//System.out.println(calculateSingularProbability(currentMover));
				calculateSingularProbability(currentMover);
				break;
			case "goto child":
				currentMover = next(currentMover);
				break;
			case "goto parent":
				currentMover = previous(currentMover);
				break;
			case "goto sibling":
				currentMover = sibling(currentMover);
				break;
			case "add child":
				currentMover.addChild();
				break;
			case "trace device":
				System.out.println(traceDevice(currentMover));
				break;
			default:
				System.out.println("That is not a valid command.");
			}

		}

	}
	
	public static void calculateAll(Mover mover) {
		// i think you should start from the root
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
			default:
			}
			mover = mover.parent;
		}

		
		// this portion only works for two SG devices, need to apply rule for more.
		// 2 devices = 2
		// 3 devices = 4
		// 4 devices = 6
		// 5 devices = 8
		// 2x - 2
		// i will create one array slot for each vector, then multiply TODO
		
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
		
		
		for (int a = array.length - 1; a > -1; a--) {
			for (int b = 0; b < array[0].length; b++) {
				System.out.println(array[a][b]);
			}
			System.out.println("-------");
		}
		System.out.println("-------");
		for (int a = remadeArray.length - 1; a > -1; a--) {
			for (int b = 0; b < remadeArray[0].length; b++) {
				System.out.println(remadeArray[a][b]);
			}
			System.out.println("-------");
		}
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
		return 0;
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
	}

}