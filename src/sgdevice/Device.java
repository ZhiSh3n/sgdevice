package sgdevice;

public class Device {
	
	// a device will have an orientation/degree
	public String orientation;
	public int degree;
	
	public Device() {
		this.orientation = null;
		this.degree = 0;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	public String getOrientation() {
		return this.orientation;
	}
	
	public int getDegree() {
		return this.degree;
	}

}
