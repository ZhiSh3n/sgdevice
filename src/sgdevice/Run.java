package sgdevice;

import java.util.ArrayList;
import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		
		Node root = new Node(null, 0);
		
		while (true) {
			reader();
		}
		
	}
	
	public static void reader() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Current Node: " + );
		System.out.println("Commands: ");
		System.out.println("  [parent] : moves to parent node");
		System.out.println("  [sibling] : moves to sibling node");
		System.out.println("  [children] : moves to first children node");
		System.out.println("  [probability] : displays probability");
		System.out.println("  [create children] : create children");
	}
}