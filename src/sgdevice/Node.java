package sgdevice;

import java.util.ArrayList;
import java.util.Scanner;

public class Node {
	
	private Node parent;
	private int layer;
	private ArrayList<Node> children;
	private Device device;
	
	public Node(Node parent, int layer) {
		this.layer = layer;
		this.device = new Device();
		System.out.println("Choose an orientation [z][x][other]: ");
		orientationChooser(this.device);
	}

	public void create() {
		if (this.children.size() > 2) {
			System.out.println("You can't create any more children for this node.");
		} else {
			Node child = new Node(this, (this.layer+1));
		}
	}
	
	public int getDegree() {
		return this.device.degree;
	}
	
	public String getOrientation() {
		return this.device.orientation;
	}
	
	public static void orientationChooser(Device device) {
		Scanner reader = new Scanner(System.in);
		String z = new String("z");
		String x = new String("x");
		String other = new String("other");
		int degree;
		String orientation = reader.nextLine();
		String compare = new String(orientation);
		if (compare.equals(other)) {
			// if equals other, ask for a degree
			System.out.println("Choose a degree: ");
			degree = reader.nextInt();
			device.setDegree(degree);
		}
		if ((!compare.equals(z) && !compare.equals(x)) && !compare.equals(other)) {
			System.out.println("That is an unacceptable value. Try again.");
			orientationChooser(device);
		} else {
			device.setOrientation(orientation);
		}
	}

}
