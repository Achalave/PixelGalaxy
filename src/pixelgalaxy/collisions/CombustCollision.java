
package pixelgalaxy.collisions;

import pixelgalaxy.*;

public class CombustCollision implements Collision{

    @Override
    public void interact(Moveable m1, Moveable m2, String[] args) {
        if(m1 instanceof Combustable){
            Mechanics.combustMoveable(m1);
        }else if(m2 instanceof Combustable){
            Mechanics.combustMoveable(m2);
        }else{
            Mechanics.collisions.get(StickCollision.class).interact(m1, m2, args);
        }
    }

    
}
