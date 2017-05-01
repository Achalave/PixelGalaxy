package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Earth extends Particle {

    public Earth() {
        super();
    }

    public Earth(int x, int y, int sx, int sy) {
        super(x, y, sx, sy);

    }

    @Override
    public Image getImage() {
        return Art.images.get("/particles/Earth.png");
    }

    @Override
    public int getMass() {
        return 10;
    }

    @Override
    protected int getStartingTemperature() {
        return 72;
    }

    @Override
    public double getHeatCoeficient() {
        return 10;
    }
}
