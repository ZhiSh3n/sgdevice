package sgdevice;

import java.util.Scanner;

public class Run {

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
			System.out.println("_____________________________________________________________");
			System.out.println("Commands: ");
			System.out.println("   [view] : view node information");
			System.out.println("   [goto child] : navigate to child number # (starting from 0)");
			System.out.println("   [goto parent] : navigate to the parent");
			System.out.println("   [goto sibling] : navigate to a sibling #");
			System.out.println("   [add child] : add a child (you will be promped to define an SG Device");
			String line = scanner.nextLine();

			switch (line) {
			case "view":
				view(currentMover);
				break;
			case "goto child":
				currentMover = next(currentMover);
				break;
			case "goto parent":
				currentMover = previous(currentMover);
				break;
			case "goto sibling":
				break;
			case "add child":
				currentMover.addChild();
				break;
			default:
			}
			
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