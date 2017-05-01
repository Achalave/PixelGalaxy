package pixelgalaxy.collisions;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pixelgalaxy.*;

public class TouchReactionCollision implements Collision{

    @Override
    public void interact(Moveable m1, Moveable m2, String[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    @Override
//    public void interact(int ci1, Cluster c1, int ci2, Cluster c2, int pi1, Particle p1, int pi2, Particle p2, String[] args) {
//        if (c1 == null) {
//            new StickCollision().interact(ci1, c1, ci2, c2, pi1, p1, pi2, p2, args);
//            return;
//        }
//        ArrayList<String> befores = new ArrayList<String>();
//        ArrayList<String> afters = new ArrayList<String>();
//        ArrayList<Particle> disposal = new ArrayList<Particle>();
//        ArrayList<Particle> disposal2 = new ArrayList<Particle>();
//        boolean explosive = false;
//        boolean next = false;
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].equals("*")) {
//                if (!next) {
//                    next = true;
//                } else {
//                    explosive = Boolean.getBoolean(args[i + 1]);
//                    break;
//                }
//            } else {
//                if (next) {
//                    afters.add(args[i]);
//                } else {
//                    befores.add(args[i]);
//                }
//            }
//        }
//        removeConnections(befores, c1, p1, disposal);
//        if (c2 != null) {
//            removeConnections(befores, c2, p2, disposal2);
//        } else {
//            befores.remove(p2.getClass().getName());
//            disposal2.add(p2);
//        }
//        //TODO
//
//        if (befores.isEmpty()) {
//            Rectangle bounds = null;
//            Moveable two;
//            //Remove the particles and find a common bounds
//            //set the speed using conservation of momentum of removed particles
//            int totalMass = 0;
//            float sx = 0;
//            float sy = 0;
//            for (Particle p : disposal) {
//                c1.removeParticle(p);
//                if (bounds == null) {
//                    bounds = p.getBounds();
//                } else {
//                    bounds.union(p.getBounds());
//                }
//                totalMass += p.getMass();
//                sx += p.getMass() * c1.getSpeedX();
//                sy += p.getMass() * c1.getSpeedY();
//            }
//            if (c2 != null) {
//                two = c2;
//                for (Particle p : disposal2) {
//                    c2.removeParticle(p);
//                    bounds.union(p.getBounds());
//                    totalMass += p.getMass();
//                    sx += p.getMass() * two.getSpeedX();
//                    sy += p.getMass() * two.getSpeedY();
//                }
//            } else {
//                two = p2;
//                GamePanel.particles.remove(p2);
//                bounds.union(p2.getBounds());
//                totalMass += p2.getMass();
//                sx += p2.getMass() * two.getSpeedX();
//                sy += p2.getMass() * two.getSpeedY();
//            }
//
//
//
//            //Create the new particles
//            int x = (int) (bounds.getX() + bounds.getWidth() / 2), y = (int) (bounds.getY() + bounds.getHeight() / 2);
//            double rads;
//            ArrayList<Particle> news = new ArrayList<Particle>();
//            for (String s : afters) {
//                try {
//                    news.add((Particle) Class.forName(s).newInstance());
//                } catch (InstantiationException ex) {
//                    Logger.getLogger(TouchReactionCollision.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(TouchReactionCollision.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(TouchReactionCollision.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            //Postion the new particles
//            news.get(0).setLocation(x - news.get(0).getWidth() / 2, y - news.get(0).getHeight() / 2);
//            for (int i = 0; i + 1 < news.size(); i++) {
//                Particle p = news.get(i);
//                //setup new x y
//                rads = Math.random() * Math.PI * 2;
//                Particle part = news.get(i + 1);
//                boolean valid;
//                do {
//                    int tempX = x;
//                    int tempY = y;
//                    //Top
//                    if (rads >= Math.PI / 4 && rads <= 3 * Math.PI / 4) {
//                        tempY += -p.getHeight() / 2 - news.get(i + 1).getHeight() / 2;
//                        tempX += (-p.getHeight() / 2) / Math.tan(rads);
//                    } //Bottom
//                    else if (rads >= 5 * Math.PI / 4 && rads <= 7 * Math.PI / 4) {
//                        tempY += p.getHeight() / 2 + news.get(i + 1).getHeight() / 2;
//                        tempX += (p.getHeight() / 2) / Math.tan(rads);
//                    } //Left
//                    else if (rads > 3 * Math.PI / 4 && rads < 5 * Math.PI / 4) {
//                        tempX += -p.getWidth() / 2 - news.get(i + 1).getWidth() / 2;
//                        tempY += (-p.getWidth() / 2) * Math.tan(rads);
//                    } //Right
//                    else if (rads >= 7 * Math.PI / 4 || rads <= Math.PI / 4) {
//                        tempX += p.getWidth() / 2 + news.get(i + 1).getWidth() / 2;
//                        tempY += (p.getWidth() / 2) * Math.tan(rads);
//                    }
//                    part.setLocation(tempX - part.getWidth() / 2, tempY - part.getHeight() / 2);
//                    valid = true;
//                    for (int k = 0; k < i; k++) {
//                        if (news.get(k).getBounds().intersects(part.getBounds())) {
//                            valid = false;
//                        }
//                    }
//                    if (valid) {
//                        x = tempX;
//                        y = tempY;
//                    } else {
//                        rads += Math.PI / 10;
//                        if (rads > Math.PI * 2) {
//                            rads -= Math.PI * 2;
//                        }
//                    }
//                } while (!valid);
//
//            }
//            Cluster c = null;
//            if (explosive) {
//                for (Particle p : news) {
//                    GamePanel.particles.add(p);
//                }
//            } else {
//                c = new Cluster();
//                for (Particle p : news) {
//                    c.addParticle(p);
//                }
//                sx /= totalMass;
//                sy /= totalMass;
//                c.setSpeed(sx, sy);
//                GamePanel.clusters.add(c);
//            }
//            if (c1 == GamePanel.lock) {
//                if (c1.getParticles().isEmpty() && c != null) {
//                    GamePanel.lock = c;
//                    return;
//                }
//            }
//            if (c2 != null && GamePanel.lock != null && c2 == GamePanel.lock) {
//                if (c2.getParticles().isEmpty() && c != null) {
//                    GamePanel.lock = c;
//                }
//            }
//        } else {
//            new StickCollision().interact(ci1, c1, ci2, c2, pi1, p1, pi2, p2, args);
//        }
//    }
//
//    private void removeConnections(ArrayList<String> parts, Cluster c, Particle p, ArrayList<Particle> d) {
//        if (parts.isEmpty()) {
//            return;
//        }
//        if (parts.remove(p.getClass().getName())) {
//            d.add(p);
//            if (parts.isEmpty()) {
//                return;
//            } else {
//                for (Particle pt : c.getIntersectingParticles(p)) {
//                    removeConnections(parts, c, pt, d);
//                }
//            }
//        }
//    }
}
