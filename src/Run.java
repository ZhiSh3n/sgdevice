import java.util.ArrayList;
import java.util.Scanner;

// TODO
/*
the nature of
- MERGING: essentially you ignore the device that has its children nodes merge
- CLOSING:
    - where you take the probability of the closed sibling and add that to yours
    - for example where there
 */

public class Run {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        welcomeText();
        promptEnterKey();

        // make a new Mover with a null parent
        Mover root = new Mover(0, null, true);
        // set the currentMover equal to this root Mover
        Mover currentMover = root;

        // this is the prompter
        while (true) {
            scanner.nextLine();
            for (int i = 0; i < 10; i++) {
                System.out.println("");
            }
            Mover.view(currentMover);
            System.out.println("");
            System.out.println("Commands: ");
            System.out.println("   [calculate] : calculate eigenvectors for particular result | please run this from the desired open node");
            System.out.println("   [goto child] : navigate to child number # (starting from 0)");
            System.out.println("   [goto parent] : navigate to the parent");
            System.out.println("   [goto sibling] : navigate to a sibling #");
            System.out.println("   [add child] : add a child (you will be promped to define an SG Device");
            System.out.println("   [trace device] : how many devices in a row");
            System.out.println("   [algorithm] : calculate all | this can be run anywhere");
            // Adding some white space for aesthetics.
            String line = scanner.nextLine();
            for (int i = 0; i < 10; i++) {
                System.out.println("");
            }
            switch (line) {
                case "calculate":
                    // Set to TRUE to see individual Eigenvectors.
                    System.out.println(Mover.calculateSingularProbability(currentMover, true));
                    break;
                case "goto child":
                    currentMover = Mover.next(currentMover); // TODO add functionality for [add child #]
                    break;
                case "goto parent":
                    currentMover = Mover.previous(currentMover);
                    break;
                case "goto sibling":
                    currentMover = Mover.sibling(currentMover);
                    break;
                case "add child":
                    Mover.addChild(currentMover, 2); // TODO add functionality for [add child #]
                    break;
                case "trace device":
                    System.out.println(Mover.traceDevice(currentMover));
                    break;
                case "refresh":
                    Mover.refreshEigenvectors(currentMover);
                    break;
                case "algorithm":
                    // create an ArrayList to hold all the movers
                    ArrayList<Mover> algorithmHolder = new ArrayList<>();

                    // create a holder object -- basically a counter
                    int counter = 0;
                    Holder holder = new Holder();
                    holder.setVal(counter);

                    // initiate cycler
                    Mover.cycler(currentMover, holder, algorithmHolder);

                    // The below is for debugging purposes.

                    System.out.println("Counts, excluding root: " + holder.getVal());
                    System.out.println("Counts size of arraylist, including root: " + algorithmHolder.size());

                    System.out.println("Displays each element (layer) in the arraylist");
                    for (int i = 0; i < algorithmHolder.size(); i++) {
                        System.out.println(algorithmHolder.get(i).layer);
                    }
                    ArrayList<Mover> refinedHolder = new ArrayList<>();
                    for (int i = 0; i < algorithmHolder.size(); i++) {
                        if (algorithmHolder.get(i).children.size() == 0) {
                            // if the mover has no children...
                            refinedHolder.add(algorithmHolder.get(i));
                        }
                    }
                    System.out.println("Refined holder");
                    for (int i = 0; i < refinedHolder.size(); i++) {
                        System.out.println(refinedHolder.get(i).layer);
                    }
                    // now we have an arraylist of all the movers that are at the end of their branches...
                    // let's iterate through this array
                    //System.out.println("-------------");
                    for (int i = 0; i < refinedHolder.size(); i++) {
                        currentMover = refinedHolder.get(i);
                        Mover temporaryMover = currentMover;
                        for (int a = 0; a < Mover.traceDevice(currentMover); a++) {


                            // If the currentMover isn't the root, then we'll ask for it's position as a child.
                            if (temporaryMover.parent != null) {
                                if (temporaryMover.parent.children.indexOf(temporaryMover) == 0) {  //
                                    System.out.print("[+]");
                                } else if (temporaryMover.parent.children.indexOf(temporaryMover) == 1) {
                                    System.out.print("[-]");
                                }
                            }
                            System.out.print(temporaryMover.layer);
                            if (a != Mover.traceDevice(currentMover) -1) {
                                System.out.print(" <- ");
                            }

                            if (a == Mover.traceDevice(currentMover) -1) {
                                System.out.println("");
                            }
                            temporaryMover = temporaryMover.parent;
                        }
                        System.out.print    ("Probability: ");
                        System.out.println(Mover.calculateSingularProbability(currentMover, false));
                    }

                    break;
                default:
                    // TODO here i am trying to do the add child #
                    // TODO but it is failing because i need to make 2 children for every node
				/*
				 * Search the first half of the [line] to see if there is the word [add].
				 * If there is, then the user is trying to type [add child].
				 */
                    int lineLength = line.length();
                    if (line.substring(0,lineLength/2).contains("add")) {
					
					/*
					 * If [true], I split the string and get the last element, which should be 
					 * the integer. I will set this integer to [childNumber].
					 */
                        String[] lineSplit = line.split(" ");
                        try {
                            int childNumber = Integer.parseInt(lineSplit[lineSplit.length-1]);
                            Mover.addChild(currentMover, childNumber);

                        } catch (NumberFormatException e) {
                            System.out.println("INVALID");
                        }
                    } else {
                        System.out.println("That is not a valid command.");
                    }
            }
        }
    }

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void welcomeText() {
        System.out.println("");
        System.out.println("Welcome to SGD-Calculator.");
        System.out.println("==========================");
        System.out.println("");
        System.out.println("This calculator is able to solve two types of problems:");
        System.out.println("");
        System.out.println("  - Custom Eigenvector");
        System.out.println("  - Theta Oriented");
        System.out.println("");
        System.out.println("You will now be prompted to decide on which type of problem you want to pursue.");
        System.out.println("If want to work with Custom Eigenvectors, choose the 'eigenvector' option when prompted.");
        System.out.println("Otherwise, you may choose any of the other available options.");
        System.out.println("");
    }

}