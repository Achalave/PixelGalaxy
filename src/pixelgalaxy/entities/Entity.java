
package pixelgalaxy.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import pixelgalaxy.Moveable;

public abstract class Entity extends Moveable{
    protected double angle = Math.PI/4;
    
    public Entity(){
        super();
    }
    
    public Entity(int x, int y, int sx, int sy) {
        super(x,y,sx,sy);
    }
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        Graphics2D g2 = (Graphics2D)g;
        Image i = getImage();
        AffineTransform af = new AffineTransform();
        af.rotate(angle, x+i.getWidth(null)/2, y+i.getHeight(null)/2);
        af.translate(x, y);
        g2.drawImage(getImage(), af, null);
    }

    public void moveTo(int x, int y){
        
    }
    
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    
    
    public abstract Image getImage();
}
