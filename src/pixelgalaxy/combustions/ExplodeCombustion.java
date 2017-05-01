
package pixelgalaxy.combustions;

import pixelgalaxy.*;

public class ExplodeCombustion implements Combustion{

    @Override
    public void combust(Moveable[] ps, Moveable m, String[] args) {
        Moveable p = ps[0];
        m.becomeFree();
        int x = (int)(p.getX()+p.getWidth()/2);
        int y = (int)(p.getY()+p.getWidth()/2);
        if(p instanceof Explosive)
            Mechanics.explode(x, y, ((Explosive)p).getForce());
        Mechanics.combust(x, y, ((Combustable)p).getRadius());
    }
}
