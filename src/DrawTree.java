public class DrawTree {

    // Given any random Mover in an experiment, return the root Mover
    public static Mover returnRoot(Mover randomMover) {
        while (randomMover.parent != null) {
            randomMover = randomMover.parent;
        }
        return randomMover;
    }

    // pass any mover into here
    public static void draw(Mover randomMover) {

        // get the root Mover
        Mover root = returnRoot(randomMover);
        // print out the orientation of the root Mover
        System.out.println("+" + root.device.orientation);
        drawRest(root);
    }

    // draw the rest of the tree
    public static void drawRest(Mover mover) {
        // if there are children...
        for (int i = 0; i < mover.children.size(); i++) {
            if (mover.children.size() == 1) {

            } else {

            }
            drawRest(mover.children.get(i));
        }
    }

}
