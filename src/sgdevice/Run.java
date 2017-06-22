package sgdevice;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Mover root = new Mover(0, null);
		Mover currentMover = root;
		
		while (true) {
			scanner.nextLine();
			for (int i = 0; i < 10; i++) {  
				System.out.println("");
			}
			Mover.view(currentMover);
			System.out.println("");
			System.out.println("Commands: ");
			System.out.println("   [calculate] : calculate eigenvectors for particular result");
			System.out.println("   [goto child] : navigate to child number # (starting from 0)");
			System.out.println("   [goto parent] : navigate to the parent");
			System.out.println("   [goto sibling] : navigate to a sibling #");
			System.out.println("   [add child] : add a child (you will be promped to define an SG Device");
			System.out.println("   [trace device] : how many devices in a row");

			// Adding some white space for aesthetics.
			String line = scanner.nextLine();
			for (int i = 0; i < 10; i++) {
				System.out.println("");
			}

			switch (line) {
			case "calculate":
				System.out.println(Mover.calculateSingularProbability(currentMover));
				break;
			case "goto child":
				currentMover = Mover.next(currentMover);
				break;
			case "goto parent":
				currentMover = Mover.previous(currentMover);
				break;
			case "goto sibling":
				currentMover = Mover.sibling(currentMover);
				break;
			case "add child":
				Mover.addChild(currentMover);
				break;
			case "trace device":
				System.out.println(Mover.traceDevice(currentMover));
				break;
			default:
				System.out.println("That is not a valid command.");
			}

		}

	}
	

}