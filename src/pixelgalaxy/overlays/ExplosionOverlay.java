/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelgalaxy.overlays;

import java.awt.Color;
import java.awt.Graphics;
import pixelgalaxy.StaticGraphicOverlay;

public class ExplosionOverlay implements StaticGraphicOverlay{
    float x, y, radius = 0;
    static final float change = 300;
    int maxRadius = 50;
    
    public ExplosionOverlay(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public void shift(float dx, float dy) {
        x += dx;
        y += dy;
    }

    @Override
    public boolean draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
        if(radius >= maxRadius)
            return false;
        else 
            return true;
    }

    @Override
    public void tic(long millis) {
        radius += change*(millis/1000.0);
    }
    
}
