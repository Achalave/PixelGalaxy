package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Silicon extends Particle {

    public Silicon() {
        super();
    }

    public Silicon(int x, int y, int sx, int sy) {
        super(x, y, sx, sy);

    }

    @Override
    public Image getImage() {
        return Art.images.get("/particles/Silicon.png");
    }

    @Override
    public int getMass() {
        return 10;
    }
    
    @Override
    protected int getStartingTemperature() {
        return 85;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 200;
    }
}