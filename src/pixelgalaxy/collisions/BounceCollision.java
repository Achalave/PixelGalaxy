package pixelgalaxy.collisions;

import pixelgalaxy.Moveable;

public class BounceCollision implements Collision {

    @Override
    public void interact(Moveable m1, Moveable m2, String[] args) {
        int ticTime = 15;
        int mass1 = m1.getTotalMass(),mass2 = m2.getTotalMass();
        //Determine the side of the collision
        double theta = Math.atan2(m1.getY() - m2.getY(), m1.getX() - m2.getX());
        //right or left
        if ((theta >= 0 && theta < Math.PI / 4) || (theta > 7 * Math.PI / 4 && theta <= 0)
                || (theta > 3 * Math.PI / 4 && theta < 5 * Math.PI / 4)) {
            //Caclulate the new speeds
            float v1 = (m1.getSpeedX() * (mass1 - mass2) + 2 * mass2 * m2.getSpeedX()) / (mass1 + mass2);
            float v2 = (m2.getSpeedX() * (mass2 - mass1) + 2 * mass1 * m1.getSpeedX()) / (mass2 + mass1);
            //Going same direction so back p1 up out of the collision
            if (m1.getSpeedX() / Math.abs(m1.getSpeedX()) == m2.getSpeedX() / Math.abs(m2.getSpeedX())) {
                m1.setSpeedX(-m1.getSpeedX());
                while (m1.getCollision(m2)!=null) {
                    m1.tic(ticTime);
                    m2.tic(ticTime);
                }
                m1.tic(ticTime);
                m2.tic(ticTime);
                m1.setSpeedX(-m1.getSpeedX());
            }
            //set speeds
            m1.setSpeedX(v1);
            m2.setSpeedX(v2);
            while (m1.getCollision(m2)!=null) {
                m1.tic(ticTime);
                m2.tic(ticTime);
            }
        } else {
            //Caclulate the new speeds
            float v1 = (m1.getSpeedY() * (mass1 - mass2) + 2 * mass2 * m2.getSpeedY()) / (mass1 + mass2);
            float v2 = (m2.getSpeedY() * (mass2 - mass1) + 2 * mass1 * m1.getSpeedY()) / (mass2 + mass1);
            //Going same direction so back p1 up out of the collision
            if (m1.getSpeedY() / Math.abs(m1.getSpeedY()) == m2.getSpeedY() / Math.abs(m2.getSpeedY())) {
                m1.setSpeedY(-m1.getSpeedY());
                while (m1.getCollision(m2)!=null) {
                    m1.tic(ticTime);
                    m2.tic(ticTime);
                }
                m1.tic(ticTime);
                m2.tic(ticTime);
                m1.setSpeedY(-m1.getSpeedY());
            }
            //set speeds
            m1.setSpeedY(v1);
            m2.setSpeedY(v2);
            while (m1.getCollision(m2)!=null) {
                m1.tic(ticTime);
                m2.tic(ticTime);
            }
        }
    }
//    public void interact(int ci1, Cluster c1, int ci2, Cluster c2, int pi1, Particle p1, int pi2, Particle p2, String[] args) {
//        float mass1, mass2;
//        int ticTime = 15;
//        Moveable m1,m2;
//        if (c1 == null) {
//            mass1 = p1.getMass();
//            mass2 = p2.getMass();
//            m1 = (Moveable)p1;
//            m2 = (Moveable)p2;
//        } else if (c2 == null) {
//            mass1 = c1.getMass();
//            mass2 = p1.getMass();
//            m1 = (Moveable)c1;
//            m2 = (Moveable)p2;
//        } else {
//            mass1 = c1.getMass();
//            mass2 = c2.getMass();
//            m1 = (Moveable)c1;
//            m2 = (Moveable)c1;
//        }
//        //Determine the side of the collision
//        double theta = Math.asin((double) (p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
//        if (Double.isNaN(theta)) {
//            //Caclulate the new speeds
//            float v1 = (m1.getSpeedY()*(mass1-mass2)+2*mass2*m2.getSpeedY())/(mass1+mass2);
//            float v2 = (m2.getSpeedY()*(mass2-mass1)+2*mass1*m1.getSpeedY())/(mass2+mass1);
//            //Going same direction so back p1 up out of the collision
//            if (m1.getSpeedY() / Math.abs(m1.getSpeedY()) == m2.getSpeedY() / Math.abs(m2.getSpeedY())) {
//                m1.setSpeedY(-m1.getSpeedY());
//                while (p1.checkCollision(p2)) {
//                    m1.tic(ticTime);
//                    m2.tic(ticTime);
//                }
//                m1.tic(ticTime);
//                m2.tic(ticTime);
//                m1.setSpeedY(-m1.getSpeedY());
//            }
//            //set speeds
//            m1.setSpeedY(v1);
//            m2.setSpeedY(v2);
//            while (p1.checkCollision(p2)) {
//                m1.tic(ticTime);
//                m2.tic(ticTime);
//            }
//        } else {
//            //Caclulate the new speeds
//            float v1 = (m1.getSpeedX()*(mass1-mass2)+2*mass2*m2.getSpeedX())/(mass1+mass2);
//            float v2 = (m2.getSpeedX()*(mass2-mass1)+2*mass1*m1.getSpeedX())/(mass2+mass1);
//            //Going same direction so back p1 up out of the collision
//            if (m1.getSpeedX() / Math.abs(m1.getSpeedX()) == m2.getSpeedX() / Math.abs(m2.getSpeedX())) {
//                m1.setSpeedX(-m1.getSpeedX());
//                while (p1.checkCollision(p2)) {
//                    m1.tic(ticTime);
//                    m2.tic(ticTime);
//                }
//                m1.tic(ticTime);
//                m2.tic(ticTime);
//                m1.setSpeedX(-m1.getSpeedX());
//            }
//            //set speeds
//            m1.setSpeedX(v1);
//            m2.setSpeedX(v2);
//            while (p1.checkCollision(p2)) {
//                m1.tic(ticTime);
//                m2.tic(ticTime);
//            }
//
//        }
//    }
}
