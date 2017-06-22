Stern-Gerlach Experiment Calculator
==========================================
A Stern-Gerlach device demonstrates the intrinsic quantum property of atomic-scale systems which is that the spatial orientation of angular momentum is quantized. 

The experiment involves sending a beam of particles through a magnetic field and observing their deflection. Results show that particles possess an intrinsic angular momentum, but unlike the angular momentum of a classically spinning object, the angular momentum of a particle takes only certain quantized values.

In addition to this concept, only one component of a particle's spin can be measured at a time, meaning that taking a measurement of the spin along a particle's Z axis destroys information about the same particle's spin along the X and Y axis.

## Files
* Run.java
	* Initiates the calculator.
	* Includes a command prompt; the listed commands are sufficient to create SG devices and navigate between parent devices, children devices, and sibling devices.
	* Also includes some of the command executables, although this will most likely be moved into Mover.java.
* Device.java
	* Defines the properties and attributes of the Stern-Gerlach device.
	* Includes standard GET/SET.
	* A SG device only has one essential component, which is its Orientation. Traditionally, particles have their spin measured along the Z axis, but functionality exists for measuring spin along the X axis and a variable axis.
* Mover.java
	* A node object used to draw the SG device tree.
	* A mover object (essentially a node) has a:
		* Parent mover.
		* An ArrayList of children movers.
		* An integer dictionary.
		* An encapsulated SG device.
	* Each mover object is basically a SG device. The mover object is only used to link the SG devices together, and in this regard, the mover object is a node.
* Holder.java
	* A temporary holder object (just in case).
	* Not currently being used.

## How-to
1. When you run the project, a root node is created, and you'll be prompted to choose an orientation for this node. In essence, this is the first SG device in the tree.
```
Choose an orientation for this device [z][x][other][theta][closed][open]: 
```
2. After this, you will be presented with a list of commands.

## To Do List