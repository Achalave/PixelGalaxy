/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelgalaxy;

import devices.ThreadArray;
import devices.ThreadIterator;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pixelgalaxy.entities.Ship;
import pixelgalaxy.particles.*;

public class GamePanel extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    public static List<Moveable> pieces;
    public static List<Circle> combustions;
    public static List<Circle> explosions;
    //All graphic overlays
    public static List<GraphicOverlay> overlays;
    //The Moveable currently locked on to
    public static Moveable lock;
    //The moveable currently being hovered over
    public static Moveable hover;
    //The main loop will continue while true
    public static boolean run = true;
    //Testing Vars
    int mostParts = 0;
    int mostClusters = 0;
    long lastMaxChangeP = System.currentTimeMillis();
    long lastMaxChangeC = System.currentTimeMillis();
    long temptime = System.currentTimeMillis();
    final long startTime = System.currentTimeMillis();
    int mouseX, mouseY;

    public GamePanel() {
        initComponents();
        setBackground(Color.BLACK);
        pieces = new ThreadArray();
        combustions = new ThreadArray();
        explosions = new ThreadArray();
        overlays = new ThreadArray();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void start() {
        Mechanics.setupMechanics();
        
        //pieces.add(new Ship(500,400,0,0));
        int newX=100;
        int newY=100;
        Moveable m = new Oxygen(50+newX,50+newY,0,0);
        m.attach(new Oxygen(70+newX,50+newY,0,0));
        m.attach(new Oxygen(70+newX,70+newY,0,0));
        m.attach(new Oxygen(30+newX,50+newY,0,0));
        m.attach(new Oxygen(40+newX,80+newY,0,0));
        m.attach(new Oxygen(90+newX,20+newY,0,0));
        pieces.add(m);
        
        m = new Lava(50+newX,300+newY,0,-100);
        pieces.add(m);

        final int width = this.getWidth();
        final int height = this.getHeight();

        new Thread() {

            @Override
            public void run() {
                long time = 0, spawn = 0;
                while (run) {
                    time = System.currentTimeMillis();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    long elapsedTime = System.currentTimeMillis() - time;
                    //Used to shift eveything if there is a lock
                    float dx = 0;
                    float dy = 0;
                    boolean shiftedParts = false;
                    if (lock != null) {
                        //Find dy and dx taking lock tic into account
                    }

                    //Tic and shift every graphic overlay if needed
                    ThreadIterator<GraphicOverlay> goi = (ThreadIterator) overlays.listIterator();
                    while (goi.hasNext()) {
                        GraphicOverlay go = goi.next();
                        go.tic(elapsedTime);
                        if (go instanceof StaticGraphicOverlay) {
                            ((StaticGraphicOverlay) go).shift(dx, dy);
                        }
                    }
                    goi.dispose();

                    //Gather all of the current explosive and combustion circles
                    //that will be excecuted this tic
                    ArrayList<Circle> cCombustions = new ArrayList<Circle>();
                    ThreadIterator<Circle> i = (ThreadIterator) combustions.listIterator();
                    while (i.hasNext()) {
                        cCombustions.add(i.next());
                    }
                    i.dispose();
                    ArrayList<Circle> cExplosions = new ArrayList<Circle>();
                    i = (ThreadIterator) explosions.listIterator();
                    while (i.hasNext()) {
                        cExplosions.add(i.next());
                    }
                    i.dispose();

                    //tic everything, check collisions, and remove distant objects
                    ThreadIterator<Moveable> pi = (ThreadIterator<Moveable>) pieces.iterator();
                    while (pi.hasNext()) {
                        Moveable m = pi.next();
                        if (!shiftedParts) {
                            m.tic(elapsedTime);
                            for (Circle c : cCombustions) {
                                Mechanics.applyCombustion(c.getX(), c.getY(), c.getRadius(), m);
                            }
                            for (Circle c : cExplosions) {
                                Mechanics.applyExplosion(c.getX(), c.getY(), c.getRadius(), m);
                            }
                        }
                        ThreadIterator<Moveable> pi2 = (ThreadIterator<Moveable>) pieces.listIterator(pi.getIndex() + 1);
                        while (pi2.hasNext()) {
                            Moveable m2 = pi2.next();
                            if (!shiftedParts) {
                                m2.tic(elapsedTime);
                                for (Circle c : cCombustions) {
                                    Mechanics.applyCombustion(c.getX(), c.getY(), c.getRadius(), m2);
                                }
                                for (Circle c : cExplosions) {
                                    Mechanics.applyExplosion(c.getX(), c.getY(), c.getRadius(), m2);
                                }
                            }
                            Moveable[] col = m.getCollision(m2);
                            if (col != null) {
                                Mechanics.collide(m, m2);
                                if (!pi.hasNext() || m != pi.getCurrent()) {
                                    break;
                                }
                            }
                        }
                        pi2.dispose();
                        shiftedParts = true;
                    }
                    pi.dispose();
                    combustions.removeAll(cCombustions);
                    explosions.removeAll(cExplosions);
                    //maybe spawn things
                    repaint();
                    spawn += System.currentTimeMillis() - time;
                    if (spawn / 1000.0 >= 3) {
                        spawn -= 3 * 1000;
                        spawnRandomParticles(5);
                    }
                }
            }
        }.start();
    }

    public static void stop() {
        run = false;
    }

    public void spawnRandomParticles(int n) {
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            int sx = (((int) (Math.random() * 2) == 1) ? 1 : -1) * ((int) (Math.random() * 50) + 25);
            int sy = (((int) (Math.random() * 2) == 1) ? 1 : -1) * ((int) (Math.random() * 50) + 25);
            pieces.add(Mechanics.getRandomParticle(x, y, sx, sy));  
        }
    }

    public int getDistanceFromCenter(Moveable m) {
        int d1 = (int) Math.sqrt(Math.pow(m.getX() - this.getWidth() / 2, 2) + Math.pow(m.getY() - this.getHeight() / 2, 2));
        int d2 = (int) Math.sqrt(Math.pow(m.getX() + m.getWidth() - this.getWidth() / 2, 2) + Math.pow(m.getY() + m.getHeight() - this.getHeight() / 2, 2));
        return (d1 > d2) ? d1 : d2;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            ThreadIterator<Moveable> pi = (ThreadIterator) pieces.listIterator();
            while (pi.hasNext()) {
                pi.next().draw(g);
            }
            pi.dispose();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("PAINT ERROR");
        }
        if (lock != null) {
            g.setColor(Color.yellow);
            Rectangle r = lock.getTotalBounds();
            ((Graphics2D) g).draw(r);
        }
        if (hover != null) {
            g.setColor(Color.WHITE);
            g.drawString(hover.getClass().getSimpleName(), (int) hover.getX(), (int) hover.getY());
            //g.drawString(hover.getClass().getSimpleName(), (int) hover.getX(), (int) hover.getY());
            g.drawString("Temp: " + hover.getTemperature(), (int) hover.getX(), (int) hover.getY()-10);

        }
        //Draw the overlays
        ThreadIterator<GraphicOverlay> goi = (ThreadIterator) overlays.listIterator();
        while (goi.hasNext()) {
            GraphicOverlay go = goi.next();
            if (!go.draw(g)) {
                goi.remove();
            }
        }
        goi.dispose();

        g.setColor(Color.yellow);
        g.drawString("(" + mouseX + ", " + mouseY + ")", 10, 30);
        //g.drawString("There are "+tempCount+" particles.", 10, 50);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 527, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 388, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public Moveable getMoveableAt(Point pnt) {
        ThreadIterator<Moveable> pi = (ThreadIterator<Moveable>) pieces.listIterator();
        while (pi.hasNext()) {
            Moveable m = pi.next().getMoveableAt(pnt);
            if (m != null) {
                pi.dispose();
                return m;
            }
        }
        pi.dispose();
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //set the lock
        Moveable m = getMoveableAt(me.getPoint());
        lock = m;
        if (m == null) {
            Mechanics.explode(me.getX(), me.getY(), 25);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        Moveable m = getMoveableAt(me.getPoint());
        hover = m;
        mouseX = me.getX();
        mouseY = me.getY();
    }
}
