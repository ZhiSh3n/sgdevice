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
	}
	
	public void addChild() {
		this.children.add(new Mover(this.layer+1, this));
	}
	

}
