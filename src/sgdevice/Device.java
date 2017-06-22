package sgdevice;

public class Device {
	
	public String orientation;
	public double degree;
	
	public Device() {
		this.orientation = null;
		this.degree = 0;
	}
	
	public void setDegree(double degree) {
		this.degree = degree;
	}
	
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	public String getOrientation() {
		return this.orientation;
	}
	
	public double getDegree() {
		return this.degree;
	}

}
