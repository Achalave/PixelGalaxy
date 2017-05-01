
package pixelgalaxy.entities;

import java.awt.Image;
import java.awt.Rectangle;
import pixelgalaxy.Art;

public class Ship extends Entity{
    Image image;
    
    public Ship(){
        super();
        image = Art.images.get("/entities/ship/Ship.png");
    }
    
    public Ship(int x, int y, int sx, int sy) {
        super(x,y,sx,sy);
        image = Art.images.get("/entities/ship/Ship.png");
    }
    
    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,(int)getImage().getWidth(null),(int)getImage().getHeight(null));
    }

    @Override
    public int getMass() {
        return 35;
    }

    @Override
    public double getHeatCoeficient() {
       return 200;
    }

    @Override
    protected int getStartingTemperature() {
        return 80;
    }

    
}
