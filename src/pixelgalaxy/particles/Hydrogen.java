
package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.*;
import pixelgalaxy.combustions.ExplodeCombustion;
import pixelgalaxy.collisions.CombustCollision;
import pixelgalaxy.collisions.StickCollision;

public class Hydrogen extends Particle implements Combustable{

    public Hydrogen(){
        super();
    
    }
    
    public Hydrogen(int x, int y, int sx, int sy){
        super(x,y,sx,sy);
        
    }
    
    @Override
    public Image getImage() {
        return Art.images.get("/particles/Hydrogen.png");
    }

    @Override
    public int getMass() {
        return 5;
    }
    
    @Override
    protected int getStartingTemperature() {
        return 75;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 30;
    }

    @Override
    public int getRadius() {
        return 10;
    }
}

