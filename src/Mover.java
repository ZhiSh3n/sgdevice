import java.util.ArrayList;
import java.util.Scanner;

public class Mover {

    public int layer;
    public ArrayList<Mover> children;
    public Mover parent;
    public Device device;

    public Mover(int layer, Mover parent, boolean isManual) {

        // every Mover will have these variables
        this.layer = layer;
        this.children = new ArrayList<>();
        this.parent = parent;
        this.device = new Device();

        if (isManual) {
            // if we are creating a Mover manually, choose an orientation for it
            orientationChooser(this.device, this);

            // then, we will create a placeholderChild for it
            // since we pass isManual == false, the placeholderChild will be given an orientation
            Mover placeholderChildOne = new Mover(layer + 1, this, false);
            Mover placeholderChildTwo = new Mover(layer + 1, this, false);

            // if our Mover is the root, only add one placeholderChild
            // else, add two placeholderChild(s)
            if (this.parent == null) {
                this.children.add(placeholderChildOne);
            } else {
                this.children.add(placeholderChildOne);
                this.children.add(placeholderChildTwo);
            }
        } else {
            this.device.setOrientation("open");
        }
    }

    // This method iterates through every Mover object in a tree.
    public static void cycler(Mover mover, Holder holder, ArrayList list) {
        // TODO recognize open, closed, and merge Mover configurations.

        // add the root mover to list
        if (mover.parent == null) {
            list.add(mover);
        }
        //
        for (int i = 0; i < mover.children.size(); i++) {
            refreshEigenvectors(mover.children.get(i));
            list.add(mover.children.get(i));
            int tmp = (int) holder.getVal();
            tmp = tmp + 1;
            holder.setVal(tmp);
            cycler(mover.children.get(i), holder, list);
        }

    }

    // This method calculates the probability of an SG configuration for a linear link of SG devices.
    public static double calculateSingularProbability(Mover mover, Boolean bool) {
        if (mover.parent == null || mover.parent.parent == null ) {
            System.out.println("You do not have any functional devices set up.");
            return 0;
        } else {
            double[][] array = new double[traceDevice(mover) - 1][2];
            for (int counter = traceDevice(mover) - 2; counter > -1; counter--){
                array[counter][0] = mover.device.firstEigenvectorComponent;
                array[counter][1] = mover.device.secondEigenvectorComponent;
                mover = mover.parent;
            }
            int remadeArrayLength = (array.length * 2) - 2;
            double[][] remadeArray = new double[remadeArrayLength][2];
            remadeArray[0][0] = array[0][0];
            remadeArray[0][1] = array[0][1];
            remadeArray[remadeArray.length - 1][0] = array[array.length - 1][0];
            remadeArray[remadeArray.length - 1][1] = array[array.length - 1][1];
            for(int a = array.length - 2; a > 0; a--) {
                remadeArray[(2*a)][0] = array[a][0];
                remadeArray[(2*a)][1] = array[a][1];
                remadeArray[(2*a)-1][0] = array[a][0];
                remadeArray[(2*a)-1][1] = array[a][1];
            }

            // This if-statement is a toggle used for debugging; it allows you to verify individual Eigenvectors.
            if (bool == true) {
                System.out.println("Individual Eigenvectors:");
                System.out.println("");
                for (int a = array.length - 1; a > -1; a--) {
                    for (int b = 0; b < array[0].length; b++) {
                        System.out.println(array[a][b]);
                    }
                    System.out.println("-------");
                }
                System.out.println("");
                System.out.println("");
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
            }



            // Third array is used to hold the contents of (multiple) cross products.
            double[] finalArray = new double[remadeArray.length / 2];

            // Store contents in third array.
            for(int a = remadeArray.length -1; a > -1; a = a - 2) {
                double result = 1;
                result = (remadeArray[a][0] * remadeArray[a-1][0]) + (remadeArray[a][1] * remadeArray[a-1][1]);
                finalArray[((a+1)/2) - 1] = result;
            }

            // Multiply values in the third array together and square the result.
            double toPrint = 1;
            for(int a = finalArray.length - 1; a > -1; a--) {
                toPrint *= finalArray[a];
            }
            toPrint = Math.pow(toPrint, 2);

            // Print probability.
            return toPrint;
        }
    }

    public static void refreshEigenvectors(Mover mover) {
        if (mover.parent == null) {
            // The root Mover has NO Eigenvector; README for more details.
        } else {
            switch (mover.parent.device.orientation) {
                case "x":
                    if (mover.parent.children.indexOf(mover) == 0) {
                        mover.device.firstEigenvectorComponent = Math.sqrt(0.5);
                        mover.device.secondEigenvectorComponent = Math.sqrt(0.5);
                    } else {
                        mover.device.firstEigenvectorComponent = Math.sqrt(0.5);
                        mover.device.secondEigenvectorComponent = -1 * Math.sqrt(0.5);
                    }
                    break;
                case "z":
                    if (mover.parent.children.indexOf(mover) == 0) {
                        mover.device.firstEigenvectorComponent = 1;
                        mover.device.secondEigenvectorComponent = 0;
                    } else {
                        mover.device.firstEigenvectorComponent = 0;
                        mover.device.secondEigenvectorComponent = 1;
                    }
                    break;
                case "theta":
                    if (mover.parent.children.indexOf(mover) == 0) {
                        mover.device.firstEigenvectorComponent = Math.cos(0.5 * mover.parent.device.degree * Math.PI / 180);
                        mover.device.secondEigenvectorComponent = Math.sin(0.5 * mover.parent.device.degree * Math.PI / 180);
                    } else {
                        mover.device.firstEigenvectorComponent = -1 * Math.sin((1 / 2) * mover.parent.device.degree * Math.PI / 180);
                        mover.device.secondEigenvectorComponent = Math.cos((1 / 2) * mover.parent.device.degree * Math.PI / 180);
                    }
                    break;
                case "eigenvector":
                    mover.device.firstEigenvectorComponent = mover.parent.device.first;
                    mover.device.secondEigenvectorComponent = mover.parent.device.second;
                default:
            }
        }
    }

    // Method for choosing the orientation of a Mover when you first create it.
    public static void orientationChooser(Device device, Mover mover) {

        if (mover.parent == null) {
            // if TRUE, this mover is the root, so we don't want to give it the option of [closed][open]
            System.out.println("Choose an orientation for this device [z][x][theta] or an eigenvector [eigenvector]:");
        } else {
            // if FALSE, this mover is not the root, so we don't want to give it the option of [eigenvector]
            System.out.println("Choose an orientation for this device [z][x][theta] or [closed][open]: ");
        }


        Scanner reader = new Scanner(System.in);
        String z = new String("z");
        String x = new String("x");
        String theta = new String("theta");
        String closed = new String("closed");
        String eigenvector = new String("eigenvector");
        String open = new String("open");
        double degree;
        double first;
        double second;
        String orientation = reader.nextLine();
        String compare = new String(orientation);
        if (compare.equals(theta)) {
            // Choose an angle if the orientation is not z or x.
            System.out.println("Choose a degree: ");
            degree = reader.nextDouble();
            device.setDegree(degree);
        }
        if (compare.equals(eigenvector)) {
            if (mover.parent == null) {
                System.out.println("Input first component: ");
                first = reader.nextDouble();
                device.setFirst(first);
                System.out.println("Input second component: ");
                second = reader.nextDouble();
                device.setSecond(second);
            } else {
                System.out.println("DENIED: only the root device can have a custom eigenvector.");
                orientationChooser(device, mover);
            }
        }
        if (!compare.equals(z) && !compare.equals(x) && !compare.equals(theta) && !compare.equals(closed) && !compare.equals(open) && !compare.equals(eigenvector)) {
            System.out.println("That is an unacceptable value. Try again.");
            orientationChooser(device, mover);
        } else {
            device.setOrientation(orientation);
        }
    }

    // count how many children a mover has EXCLUDING open
    public static int numberOfChildrenExcludingOpen(Mover mover) {
        int counter = 0;
        for (int i = 0; i < mover.children.size(); i++) {
            if (mover.children.get(i).device.orientation != "open") {
                counter++;
            }
        }
        return counter;
    }

    // Method for adding a child to a Mover.
    public static void addChild(Mover mover, int integer) {
        String closed = new String("closed");
        String open = new String("open");


        // TODO edit this to accept numeric values
        if (integer == 2) {
            // If [true] a null value was specified so we add a child normally.
            if (numberOfChildrenExcludingOpen(mover) >= 2) {
                System.out.println("You already have the maximum number of devices attached to this device (2).");
                System.out.println("You can add no more devices.");
            } else if (mover.device.orientation.equals(closed)){
                System.out.println("This is a closed node. You can't attach a device to a closed node.");
            } else if (mover.device.orientation.equals(open)) {
                System.out.println("This is an open node. You can't attach a device to an open node.");
                System.out.println("You can replace this open node with a device by using [goto parent] and then [add child].");
            } else if ((mover.parent == null) && (numberOfChildrenExcludingOpen(mover) == 1)) {
                System.out.println("The root can only have one child.");
            } else if (numberOfChildrenExcludingOpen(mover) == 0){ // they have no real children
                System.out.println("Adding Child 0");
                mover.children.remove(0);
                mover.children.add(0, new Mover(mover.layer+1, mover, true));
                System.out.println("Child added");
            } else if (numberOfChildrenExcludingOpen(mover) == 1){ // they have one real child
                System.out.println("Adding Child 1");
                mover.children.remove(1);
                mover.children.add(1, new Mover(mover.layer+1, mover, true));
                System.out.println("Child added");
            }
        } else {
            // If a value is SPECIFIED...
            if ((integer > -1) && (integer < 2)) { // [true] if the integer is either 0 or 1
                if (mover.children.size() >= 2) {
                    System.out.println("You can add no more children");
                } else if (mover.device.orientation.equals(closed)){
                    System.out.println("This device is closed");
                } else if (mover.device.orientation.equals(open)) {
                    System.out.println("This device is open");
                } else if ((mover.parent == null) && (mover.children.size() == 1)) {
                    System.out.println("The root can only have one child.");
                    // TODO make it so that you can choose to make the child positive or negative; ie. you don't have to
                    // build 2 children in order to access the negative port.
                } else { // TODO edit this to add a child at the specified location
                    System.out.println("Adding Child " + (mover.children.size()) + " among [0,1]");
                    mover.children.add(integer, new Mover(mover.layer+1, mover, true));
                    System.out.println("Child added");

                    // you get an error because the arraylist is still size 0, make it so that
                    // you make 2 empty children when you make a mover
                }
            }
        }



    }

    // Method that, when called from a branch Mover, counts how many Movers it takes to reach the root Mover.
    public static int traceDevice(Mover mover) {
        int counter = 0;
        while (mover.parent != null) {
            mover = mover.parent;
            counter++;
        }
        return (counter + 1);
    }

    // Move to a Mover in the next layer (ie. your children); must specify a child number (0 or 1).
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

    // Move to your parent layer.
    public static Mover previous(Mover mover) {
        if (mover.parent == null) {
            System.out.println("You are already in the root node!");
            return mover;
        } else {
            System.out.println("You have moved from layer " + mover.layer + " to layer " + mover.parent.layer);
            return mover.parent;
        }
    }

    // Move to your sibling (ie. in the same layer) if you have a sibling.
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

    // Displays some information on the Mover you are currently on.
    public static void view(Mover mover) {
        refreshEigenvectors(mover);
        if (mover.layer == 0) {
            System.out.println("NOTICE: This is the root device.");
        }
        if (mover.device.orientation == "open") {
            System.out.println("Layer: " + mover.layer);
            System.out.println("This is an OPEN Mover. It has NO children.");
            System.out.println("If you want it to have children, go to its parent and use the [add child] function.");
            if (mover.parent != null) {
                System.out.print("Eigenvector: [" + mover.device.firstEigenvectorComponent + ", " + mover.device.secondEigenvectorComponent + "]");
            }
        } else {
            System.out.println("Layer: " + mover.layer);
            System.out.println("Children: " + numberOfChildrenExcludingOpen(mover));
            if (mover.parent == null) {
                System.out.println("Open nodes: " + (1 - numberOfChildrenExcludingOpen(mover)));
            } else {
                System.out.println("Open nodes: " + (2 - numberOfChildrenExcludingOpen(mover)));
            }

            if (mover.parent != null) {
                System.out.println("Element: " + mover.parent.children.indexOf(mover) + " among [0,1]");
                System.out.println("Siblings: " + (mover.parent.children.size()-1) + " out of 1");
                System.out.println("Eigenvector: [" + mover.device.firstEigenvectorComponent + ", " + mover.device.secondEigenvectorComponent + "]");
            }

            String other = new String("other");
            System.out.print("Orientation: ");
            if (mover.device.orientation.equals(other)) {
                System.out.println(mover.device.degree + "°");
            } else {
                System.out.println(mover.device.orientation);
            }
        }
    }

    // Antiquities
	/* This one isn't used anymore but could be useful in the future
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
	*/

}
