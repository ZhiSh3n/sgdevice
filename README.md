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
2. After this, you will be presented with a list of commands. From here onwards, you can choose any of the listed options, including adding children, tracing layers, etc. 
```
Commands: 
   [calculate] : calculate eigenvectors for particular result
   [goto child] : navigate to child number # (starting from 0)
   [goto parent] : navigate to the parent
   [goto sibling] : navigate to a sibling #
   [add child] : add a child (you will be promped to define an SG Device
   [trace device] : how many devices in a row
```
3. When you're ready to calculate the SG probability for a chain of devices, use the ```[calculate]``` command.


## Example Operation

## To Do List




[example](https://github.com/watson-developer-cloud/node-sdk/blob/master/examples/natural_language_classifier.v1.js)
<img src="https://visual-recognition-demo.mybluemix.net/images/samples/5.jpg" width="150" />

