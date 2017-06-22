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
	
	public void addChild() {
		String closed = new String("closed");
		String open = new String("open");
		if (this.children.size() >= 2) {
			System.out.println("You can add no more children.");
		} else if (this.device.orientation.equals(closed)){
			System.out.println("This device is closed.");
		} else if (this.device.orientation.equals(open)) {
			System.out.println("This device is open.");
		} else {
			System.out.println("Adding Child " + (this.children.size()));
			this.children.add(new Mover(this.layer+1, this));
			System.out.println("Child added.");
		}
	}
	
	public static void orientationChooser(Device device) {
		System.out.println("Choose an orientation for this device [z][x][other][closed][open]: ");
		Scanner reader = new Scanner(System.in);
		String z = new String("z");
		String x = new String("x");
		String other = new String("other");
		String closed = new String("closed");
		String open = new String("open");
		double degree;
		String orientation = reader.nextLine();
		String compare = new String(orientation);
		if (compare.equals(other)) {
			// if equals other, ask for a degree
			System.out.println("Choose a degree: ");
			degree = reader.nextDouble();
			device.setDegree(degree);
		}
		if (!compare.equals(z) && !compare.equals(x) && !compare.equals(other) && !compare.equals(closed) && !compare.equals(open)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(device);
		} else {
			device.setOrientation(orientation);
		}
	}
	

}
