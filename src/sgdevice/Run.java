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
	
	public int[] zplus;
	zplus[0] = 1;
	public int[] zminus;
	public int[] xplus;
	public int[] xminus;
	public int[] thetaplus;
	public int[] thetaminus;

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
	
	// calculate probability
	public static int calculateSingularProbability(Mover mover) {
		while (mover.parent != null) {
			mover = mover.parent;
		}
	}

	// how many devices until root?
	public static int traceDevice(Mover mover) {
		int counter = 0;
		while (mover.parent != null) {
			mover = mover.parent;
			counter++;
		}
		return (counter+1);
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