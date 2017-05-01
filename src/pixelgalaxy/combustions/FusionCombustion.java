package pixelgalaxy.combustions;

import devices.ThreadIterator;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import pixelgalaxy.GamePanel;
import pixelgalaxy.Mechanics;
import pixelgalaxy.Moveable;
import pixelgalaxy.Moveable;
import pixelgalaxy.particles.Water;

public class FusionCombustion implements Combustion {

    @Override
    public void combust(Moveable[] ps, Moveable m, String[] args) {
        int force = Integer.parseInt(args[0]);
//        Create the after Moveables
        ArrayList<Moveable> news = new ArrayList<Moveable>();
        for (int i = 1; i < args.length; i++) {
            try {
                Moveable p = (Moveable) Class.forName(args[i]).newInstance();
                p.setSpeed(p.getSpeedX(), p.getSpeedY());
                news.add(p);
            } catch (InstantiationException ex) {
                Logger.getLogger(FusionCombustion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FusionCombustion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FusionCombustion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Get the combine bounds
        Rectangle bounds = null;
        for (Moveable p : ps) {
            //Remove everything from the Moveable
            p.becomeFree();
            if (bounds == null) {
                bounds = p.getBounds();
            } else {
                bounds.union(p.getBounds());
            }
        }
        
        //Postion the new Moveables
        int x = (int) (bounds.getX() + bounds.getWidth() / 2), y = (int) (bounds.getY() + bounds.getHeight() / 2);
        double rads;
        news.get(0).setLocation(x - news.get(0).getWidth() / 2, y - news.get(0).getHeight() / 2);
        for (int i = 0; i + 1 < news.size(); i++) {
            Moveable p = news.get(i);
            //setup new x y
            rads = Math.random() * Math.PI * 2;
            Moveable part = news.get(i + 1);
            boolean valid;
            do {
                int tempX = x;
                int tempY = y;
                //Top
                if (rads >= Math.PI / 4 && rads <= 3 * Math.PI / 4) {
                    tempY += -p.getHeight() / 2 - news.get(i + 1).getHeight() / 2;
                    tempX += (-p.getHeight() / 2) / Math.tan(rads);
                } //Bottom
                else if (rads >= 5 * Math.PI / 4 && rads <= 7 * Math.PI / 4) {
                    tempY += p.getHeight() / 2 + news.get(i + 1).getHeight() / 2;
                    tempX += (p.getHeight() / 2) / Math.tan(rads);
                } //Left
                else if (rads > 3 * Math.PI / 4 && rads < 5 * Math.PI / 4) {
                    tempX += -p.getWidth() / 2 - news.get(i + 1).getWidth() / 2;
                    tempY += (-p.getWidth() / 2) * Math.tan(rads);
                } //Right
                else if (rads >= 7 * Math.PI / 4 || rads <= Math.PI / 4) {
                    tempX += p.getWidth() / 2 + news.get(i + 1).getWidth() / 2;
                    tempY += (p.getWidth() / 2) * Math.tan(rads);
                }
                part.setLocation(tempX - part.getWidth() / 2, tempY - part.getHeight() / 2);
                valid = true;
                for (int k = 0; k < i; k++) {
                    if (news.get(k).getBounds().intersects(part.getBounds())) {
                        valid = false;
                    }
                }
                if (valid) {
                    x = tempX;
                    y = tempY;
                } else {
                    rads += Math.PI / 10;
                    if (rads > Math.PI * 2) {
                        rads -= Math.PI * 2;
                    }
                }
            } while (!valid);
        }
        if (force > 0) {
            for (Moveable p : news) {
                GamePanel.pieces.add(p);
            }
            int dx = (int)(Math.random()*10)-5;
            if(dx == 0)
                dx = 1;
            int dy = (int)(Math.random()*10)-5;
            if(dy == 0)
                dy = 1;
            Mechanics.explode((int) (bounds.getX() + bounds.getWidth() / 2 + dx), (int) (bounds.getY() + bounds.getHeight() / 2 + dy), force);
        } else {
            for (int i=0;i+1<news.size();i++) {
                news.get(i).attach(news.get(i+1));
            }
            GamePanel.pieces.add(news.get(0));
        }
    }
}