
package pixelgalaxy.particles;

import java.awt.Image;
import pixelgalaxy.Art;
import pixelgalaxy.Particle;

public class Iron extends Particle{
    public Iron() {
        super();
    }

    public Iron(int x, int y, int sx, int sy) {
        super(x, y, sx, sy);

    }

    @Override
    public Image getImage() {
        return Art.images.get("/particles/Iron.png");
    }

    @Override
    public int getMass() {
        return 10;
    }

    @Override
    protected int getStartingTemperature() {
        return 90;
    }
    
    @Override
    public double getHeatCoeficient() {
        return 200;
    }
}
