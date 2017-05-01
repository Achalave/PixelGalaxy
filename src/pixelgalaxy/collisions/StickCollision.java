package pixelgalaxy.collisions;

import pixelgalaxy.GamePanel;
import pixelgalaxy.Moveable;

public class StickCollision implements Collision{

    @Override
    public void interact(Moveable m1, Moveable m2, String[] args) {
        m1.setSpeedX((m1.getTotalMass()*m1.getSpeedX()+m2.getTotalMass()*
                m2.getSpeedX())/(m1.getTotalMass()+m2.getTotalMass()));
        m1.setSpeedY((m1.getTotalMass()*m1.getSpeedY()+m2.getTotalMass()*
                m2.getSpeedY())/(m1.getTotalMass()+m2.getTotalMass()));
        GamePanel.pieces.remove(m2);
        m1.attach(m2);
    }


//    public void interact(int ci1, Cluster c1, int ci2, Cluster c2, int pi1, Particle p1, int pi2, Particle p2, String[] args) {
//        //System.out.println("STICK   "+System.currentTimeMillis());
//        Moveable m;
//        if (c1 == null) {
//            GamePanel.particles.remove(p1);
//            GamePanel.particles.remove(p2);
//            Cluster c = new Cluster(p1, p2);
//            c.setSpeedX((p1.getMass() * p1.getSpeedX() + p2.getMass() * p2.getSpeedX()) / (p1.getMass() + p2.getMass()));
//            c.setSpeedY((p1.getMass() * p1.getSpeedY() + p2.getMass() * p2.getSpeedY()) / (p1.getMass() + p2.getMass()));
//            GamePanel.clusters.add(c);
//            m=c;
//        } else if (c2 == null) {
//            GamePanel.particles.remove(p2);
//            c1.setSpeedX((p2.getMass() * p2.getSpeedX() + c1.getMass() * c1.getSpeedX()) / (p2.getMass() + c1.getMass()));
//            c1.setSpeedY((p2.getMass() * p2.getSpeedY() + c1.getMass() * c1.getSpeedY()) / (p2.getMass() + c1.getMass()));
//            c1.addParticle(p2);
//            m = c1;
//        } else {
//            GamePanel.clusters.remove(c2);
//            c1.setSpeedX((c2.getMass() * c2.getSpeedX() + c1.getMass() * c1.getSpeedX()) / (c2.getMass() + c1.getMass()));
//            c1.setSpeedY((c2.getMass() * c2.getSpeedY() + c1.getMass() * c1.getSpeedY()) / (c2.getMass() + c1.getMass()));
//            c1.addCluster(c2);
//            m = c1;
//        }
//        if(c1==GamePanel.lock&&c1!=null||c2==GamePanel.lock&&c2!=null||p1==GamePanel.lock&&p1!=null||p2==GamePanel.lock&&p2!=null){
//            GamePanel.lock = m;
//        }
//    }
}
