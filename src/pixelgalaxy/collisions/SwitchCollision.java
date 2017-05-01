package pixelgalaxy.collisions;

import pixelgalaxy.Moveable;

public class SwitchCollision implements Collision{

    @Override
    public void interact(Moveable m1, Moveable m2, String[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public void interact(int ci1, Cluster c1, int ci2, Cluster c2, int pi1, Particle p1, int pi2, Particle p2, String[] args) {
//        Class replace = null;
//        Class replaceWith = null;
//        Particle p = null;
//        Moveable m = null;
//        int di = 0;
//        try {
//            replace = Class.forName(args[0]);
//            replaceWith = Class.forName(args[1]);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(SwitchCollision.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IndexOutOfBoundsException ex) {
//            System.err.println("Incorrect reaction arguments length!");
//            Logger.getLogger(SwitchCollision.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            p = (Particle) replaceWith.newInstance();
//            p.setSpeedX((p1.getMass() * p1.getSpeedX() + p2.getMass() * p2.getSpeedX()) / (p1.getMass() + p2.getMass()));
//            p.setSpeedY((p1.getMass() * p1.getSpeedY() + p2.getMass() * p2.getSpeedY()) / (p1.getMass() + p2.getMass()));
//            if (p1.getClass() == replace) {
//                p.setLocation(p1.getX(), p1.getY());
//            } else {
//                p.setLocation(p2.getX(), p2.getY());
//            }
//        } catch (InstantiationException ex) {
//            Logger.getLogger(SwitchCollision.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(SwitchCollision.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (c1 == null) {
//            //remove
//            GamePanel.particles.remove(p1);
//            GamePanel.particles.remove(p2);
//            //add
//            GamePanel.particles.add(p);
//            m = p;
//        } else if (c2 == null) {
//            //remove
//            GamePanel.particles.remove(p2);
//            c1.removeParticle(p1);
//            //add
//            if (p1.getClass() == replace) {
//                c1.addParticle(p);
//            } else {
//                GamePanel.particles.add(p);
//                c1.update();
//            }
//            m = c1;
//        } else {
//            //remove
//            c1.removeParticle(p1);
//            c2.removeParticle(p2);
//            //add
//            if (p1.getClass() == replace) {
//                c1.addParticle(p);
//                c2.update();
//            } else {
//                c2.addParticle(p);
//                c1.update();
//            }
//        }
//        if (m != null && (c1 == GamePanel.lock && c1 != null || c2 == GamePanel.lock && c2 != null || p1 == GamePanel.lock && p1 != null || p2 == GamePanel.lock && p2 != null)) {
//            GamePanel.lock = m;
//        }
//    }
}
