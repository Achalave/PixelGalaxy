package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Water extends Particle {

    public Water() {
        super();
    }

    public Water(int x, int y, int sx, int sy) {
        super(x, y, sx, sy);

    }

    @Override
    public Image getImage() {
        return Art.images.get("/particles/Water.png");
    }

    @Override
    public int getMass() {
        return 20;
    }


    @Override
    protected int getStartingTemperature() {
        return 70;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 100;
    }
}
