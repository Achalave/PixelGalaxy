
package pixelgalaxy.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import pixelgalaxy.Art;

public class InGameMenu {
    int radius;
    ArrayList<MenuItem> items = new ArrayList<MenuItem>();
    
    public InGameMenu(int radius){
        this.radius = radius;
    }
    
    public void addMenuItem(String path, String name){
        items.add(new MenuItem(path,name));
    }
    
    public void pack(){
        double angle = 0;
        double da = 2*Math.PI/items.size();
        for(MenuItem item:items){
            int x = (int)(Math.cos(angle)*radius);
            int y = (int)(Math.sin(angle)*radius);
            item.setLocation(x, y);
            angle += da;
        }
    }
    
    public void draw(Graphics g, int x, int y){
        g.setColor(Color.RED);
        g.drawOval(x-radius, y-radius, radius*2, radius*2);
        for(MenuItem item:items){
            item.draw(g,x,y);
        }
    }
    
}

class MenuItem{
    
    Image icon;
    String name;
    int x,y;
    
    public MenuItem(String path, String name){
        this.name = name;
        icon = Art.images.get(path);
    }
    
    public void setLocation(int x,int y){
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics g, int x, int y){
        g.drawImage(icon, this.x+x, this.y+y, null);
    }
    
}
