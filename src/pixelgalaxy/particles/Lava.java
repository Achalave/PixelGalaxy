
package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Lava extends Particle{
        
    public Lava(){
        super();
    }
    
    public Lava(int x, int y, int sx, int sy){
        super(x,y,sx,sy);
        
    }
    
    @Override
    public Image getImage() {
        if(getTemperature() > 700)
            return Art.images.get("/particles/Lava.png");
        else
            return Art.images.get("/particles/LavaRock.png");
    }

    @Override
    public int getMass() {
        return 15;
    }

    @Override
    protected int getStartingTemperature() {
        return 800;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 100;
    }
}
