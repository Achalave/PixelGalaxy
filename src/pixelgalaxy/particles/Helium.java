package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Helium extends Particle {

    public Helium() {
        super();
    }

    public Helium(int x, int y, int sx, int sy) {
        super(x, y, sx, sy);

    }

    @Override
    public Image getImage() {
        return Art.images.get("/particles/Helium.png");
    }

    @Override
    public int getMass() {
        return 80;
    }

    @Override
    protected int getStartingTemperature() {
        return 30;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 10;
    }
}
