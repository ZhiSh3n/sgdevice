package sgdevice;

public class Device {
	
	public String orientation;
	public double degree;
	public double first;
	public double second;
	public double firstEigenvectorComponent;
	public double secondEigenvectorComponent;
	
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
	
	public void setFirst(double first) {
		this.first = first;
	}
	
	public void setSecond(double second) {
		this.second = second;
	}

}
