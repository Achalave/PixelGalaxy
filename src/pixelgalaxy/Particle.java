
package pixelgalaxy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Particle extends Moveable{
    
    
    
    public Particle(){
        super();
    }
    
    public Particle(int x, int y, int sx, int sy){
        this();
        this.x = x;
        this.y = y;
        this.speedX = sx;
        this.speedY = sy;
    }
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.drawImage(getImage(), (int)x, (int)y, null);
    }
    
    public boolean checkCollision(Particle g){//System.out.println(getBounds()+" "+g.getBounds()+" "+getBounds().intersects(g.getBounds()));
        return getBounds().intersects(g.getBounds());
    }
    
    
    @Override
    public int getWidth(){
        return getImage().getWidth(null);
    }
    
    @Override
    public int getHeight(){
        return getImage().getHeight(null);
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,(int)getImage().getWidth(null),(int)getImage().getHeight(null));
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    
    public abstract Image getImage();    
}
