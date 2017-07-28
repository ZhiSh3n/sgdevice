import java.util.ArrayList;
import java.util.Scanner;

/*
 * TODO should root be allowed to have 2 children? it shouldn't right? because root is an incoming 
 * quantum state and has no eigenvector of its own.
 */


public class Run {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Mover root = new Mover(0, null);
        Mover currentMover = root;

        System.out.println("");

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
            System.out.println("   [algorithm] : calculate all");
            // Adding some white space for aesthetics.
            String line = scanner.nextLine();
            for (int i = 0; i < 10; i++) {
                System.out.println("");
            }

            switch (line) {
                case "calculate":
                    // Set to TRUE to see individual Eigenvectors.
                    System.out.println(Mover.calculateSingularProbability(currentMover, false));
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
                    Mover.addChild(currentMover, 2); // TODO
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
				/*
				System.out.println("Counts, excluding root: " + holder.getVal());
				System.out.println("Counts size of arraylist, including root: " + algorithmHolder.size());
				
				System.out.println("Displays each element (layer) in the arraylist");
				for (int i = 0; i < algorithmHolder.size(); i++) {
					System.out.println(algorithmHolder.get(i).layer);
				}
				*/

                    ArrayList<Mover> refinedHolder = new ArrayList<>();
                    // this will be used to hold only the movers with NO children..
                    // and later TODO aren't closed or merged, but might only be open
                    for (int i = 0; i < algorithmHolder.size(); i++) {
                        if (algorithmHolder.get(i).children.size() == 0) {
                            // if the mover has no children...
                            refinedHolder.add(algorithmHolder.get(i));
                        }
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


}