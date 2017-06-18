package sgdevice;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Mover root = new Mover(0, null);
		Mover currentMover = root;
		view(currentMover);
		currentMover.addChild();
		view(currentMover);
		
		
		/* COMMANDS TO USE
		currentMover = next(currentMover);
		currentMover.addChild();
		currentMover = previous(currentMover);
		view(currentMover);
		*/
		
		/*
		while (true) {
			System.out.println("Commands: ");
			System.out.println("   [goto child] : navigate to ");
			System.out.println("   [goto parent] : ");
			System.out.println("   [goto sibling] : ");
			String line = scanner.nextLine();
		}
		*/
		
	}

	// move to the next layer, specifying a child number (0, 1, 2 ..)
	public static Mover next(Mover mover, int childNumber) {
		if (mover.children.size() == 0) { // if no children
			System.out.println("This mover has no children.");
			return mover;
		} else {
			if (childNumber >= mover.children.size()) {
				System.out.println("That child does not exist.");
				return mover;
			} else {
				return mover.children.get(childNumber);
			}
		}
	}
	
	// move to the previous node
	public static Mover previous(Mover mover) {
		if (mover.parent == null) {
			System.out.println("You are already in the root node!");
			return mover;
		} else {
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
		System.out.println("");
	}
	
}