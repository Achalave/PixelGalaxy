
package pixelgalaxy.collisions;

import pixelgalaxy.Moveable;

public interface Collision {
    public void interact(Moveable m1, Moveable m2, String[] args);
}
