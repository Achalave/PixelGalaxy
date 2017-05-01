package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Glass extends Particle {

    public Glass() {
        super();
    }

    public Glass(int x, int y, int sx, int sy) {
        super(x, y, sx, sy);

    }

    @Override
    public Image getImage() {
        return Art.images.get("/particles/Glass.png");
    }

    @Override
    public int getMass() {
        return 10;
    }

    @Override
    protected int getStartingTemperature() {
        return 80;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 35;
    }
}