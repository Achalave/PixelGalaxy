
package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.*;
import pixelgalaxy.combustions.ExplodeCombustion;
import pixelgalaxy.collisions.CombustCollision;
import pixelgalaxy.collisions.StickCollision;

public class Oxygen extends Particle implements Combustable, Explosive{

    public Oxygen(){
        super();
    }
    
    public Oxygen(int x, int y, int sx, int sy){
        super(x,y,sx,sy);
        
    }
    
    @Override
    public Image getImage() {
        return Art.images.get("/particles/Oxygen.png");
    }

    @Override
    public int getMass() {
        return 5;
    }
    

    @Override
    public int getRadius() {
        return getWidth()+5;
    }

    @Override
    public int getForce() {
        return 10;
    }
    
    @Override
    protected int getStartingTemperature() {
        return 75;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 50;
    }
}
