package sgdevice;

public class Run {

	public static void main(String[] args) {
		
		Mover root = new Mover(0, null);
		Mover currentMover = root;
		
		currentMover.addChild();
		currentMover.addChild();
		view(currentMover);
		
		currentMover = next(currentMover);
		view(currentMover);
		currentMover.addChild();
		currentMover.addChild();
		currentMover.addChild();
		view(currentMover);
		
		currentMover = next(currentMover);
		view(currentMover);
		
		currentMover = previous(currentMover);
		view(currentMover);
		
		
	}
	
	// move to the next node
	public static Mover next(Mover mover) {
		if (mover.children.size() == 0) {
			System.out.println("This mover has no children.");
			next(mover);
		} else {
			return mover.children.get(0);
		}
		return null;
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
		System.out.println("");
	}
	
}