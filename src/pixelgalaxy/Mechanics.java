package pixelgalaxy;

import pixelgalaxy.collisions.BounceCollision;
import pixelgalaxy.collisions.StickCollision;
import pixelgalaxy.collisions.TouchReactionCollision;
import pixelgalaxy.collisions.SwitchCollision;
import pixelgalaxy.combustions.Combustion;
import pixelgalaxy.collisions.Collision;
import devices.ThreadIterator;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pixelgalaxy.collisions.*;
import pixelgalaxy.combustions.ExplodeCombustion;
import pixelgalaxy.overlays.ExplosionOverlay;
import pixelgalaxy.particles.*;
import pixelgalaxy.combustions.FusionCombustion;

public class Mechanics {

    //Paths
    public static String controlsPath = "/resources/controls/";
    public static String imagesPath = "/resources/images/";
    public static ArrayList<Class> spawnParticles;
    public static String defaultCollision;
    public static HashMap<Class, HashMap<Class, String>> collisionData;
    public static String defaultCombustion;
    public static HashMap<Class, HashMap<Class[], String>> combustionData;
    public static HashMap<Class, Collision> collisions;
    public static HashMap<Class, Combustion> combustions;

    public static void setupMechanics() {
        //Setup Spawn Particles
        spawnParticles = new ArrayList<Class>();
        spawnParticles.add(Earth.class);
        spawnParticles.add(Water.class);
        spawnParticles.add(Lava.class);
        spawnParticles.add(Hydrogen.class);
        spawnParticles.add(Oxygen.class);
        spawnParticles.add(Helium.class);
        spawnParticles.add(Silicon.class);

        //Instaniate All Collisions
        collisions = new HashMap<Class, Collision>();
        collisions.put(BounceCollision.class, new BounceCollision());
        collisions.put(CombustCollision.class, new CombustCollision());
        collisions.put(StickCollision.class, new StickCollision());
        collisions.put(SwitchCollision.class, new SwitchCollision());
        collisions.put(TouchReactionCollision.class, new TouchReactionCollision());
        collisions.put(RandomCollision.class, new RandomCollision());
        //Instaniate All Combustions
        combustions = new HashMap<Class, Combustion>();
        combustions.put(ExplodeCombustion.class, new ExplodeCombustion());
        combustions.put(FusionCombustion.class, new FusionCombustion());

        loadCombustions();
        loadCollisions();
    }

    public static Image loadImage(String path) {
        if (!path.endsWith(".png")) {
            return null;
        }
        try {
            return ImageIO.read(Mechanics.class.getResource(path));
        } catch (IOException ex) {
            System.err.println("Failed to load image at path: " + path);
        }
        return null;
    }

    public static void loadCombustions() {
        combustionData = new HashMap<Class, HashMap<Class[], String>>();
        InputStream is = null;
        try {
            is = Mechanics.class.getResource("/resources/combustions.txt").openStream();
            Scanner s = new Scanner(is);
            defaultCombustion = s.nextLine().substring(1);
            while (s.hasNext()) {
                String line = s.nextLine();
                if (line.isEmpty() || !Character.isLetter(line.charAt(0))) {
                    continue;
                }
                //Get the prereques
                String[] parsedLine = line.split(" ");
                Class[] classes = new Class[parsedLine.length];
                for (int i = 0; i < parsedLine.length; i++) {
                    try {
                        classes[i] = Class.forName(parsedLine[i]);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Get the combustion and args
                String combustion = s.nextLine();
                for (Class cls : classes) {
                    HashMap<Class[], String> map = combustionData.get(cls);
                    if (map == null) {
                        map = new HashMap<Class[], String>();
                        combustionData.put(cls, map);
                    }
                    map.put(classes, combustion);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void loadCollisions() {
        collisionData = new HashMap<Class, HashMap<Class, String>>();
        InputStream is = null;
        try {
            is = Mechanics.class.getResource("/resources/collisions.txt").openStream();
            Scanner s = new Scanner(is);
            defaultCollision = s.nextLine().substring(1);
            while (s.hasNext()) {
                String line = s.nextLine();
                if (line.isEmpty() || !Character.isLetter(line.charAt(0))) {
                    continue;
                }
                String[] parts = line.split(" ");
                String line2 = s.nextLine();
                try {
                    Class one = Class.forName(parts[0]);
                    Class two = Class.forName(parts[1]);
                    setupCollision(one, two, line2);
                    setupCollision(two, one, line2);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void setupCollision(Class one, Class two, String c) {
        HashMap<Class, String> inner = collisionData.get(one);
        if (inner == null) {
            inner = new HashMap<Class, String>();
            collisionData.put(one, inner);
        }
        inner.put(two, c);
    }

    public static void conductHeat(Moveable p, Moveable p2, long millis) {
        double k = p.getHeatCoeficient();
        if (k > p2.getHeatCoeficient()) {
            k = p2.getHeatCoeficient();
        }
        int area;
        int dx = (int) Math.abs(p.getX() - p2.getX());
        int dy = (int) Math.abs(p.getY() - p2.getY());
        if (dx < dy) {
            int width = p.getWidth();
            if (width > p2.getWidth()) {
                width = p2.getWidth();
            }
            area = width - dx;
        } else {
            int height = p.getHeight();
            if (height > p2.getHeight()) {
                height = p2.getHeight();
            }
            area = height - dy;
        }

        double rate = k * area * (p.getTemperature() - p2.getTemperature()) / 10000;
        float dt = (float) (rate * (millis / 1000.0));
        p.setTemperature(p.getTemperature() - dt);
        p2.setTemperature(p2.getTemperature() + dt);
    }

    public static void collide(Moveable m, Moveable m2) {
        String line = defaultCollision;
        HashMap<Class, String> map = collisionData.get(m.getClass());
        if (map != null) {
            line = map.get(m2.getClass());
            if (line == null) {
                line = defaultCollision;
            }
        }
        int index = line.indexOf(" ");
        String[] args;
        if (index < 0) {
            args = null;
        } else {
            args = line.substring(index + 1).split(" ");
            line = line.substring(0, index);
        }
        try {
            collisions.get(Class.forName(line)).interact(m, m2, args);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.err.println("In Mechanics.collide, the class: " + line + " was not found in collisions.");
        }
    }

    public static void combustMoveable(Moveable m) {
        if (!(m instanceof Combustable)) {
            return;
        }
        String line = defaultCombustion;
        Moveable[] chain = {m};

        //Look for any specific combustion
        HashMap<Class[], String> map = combustionData.get(m.getClass());
        if (map != null) {
            Set<Class[]> combos = map.keySet();
            for (Class[] combo : combos) {
                List comboList = new ArrayList<Class>(Arrays.asList(combo));
                comboList.remove(m.getClass());
                List<Moveable> solution = new ArrayList<Moveable>();
                solution.add(m);
                if (isValidCombo(m, comboList, solution)) {
                    line = map.get(combo);
                    chain = new Moveable[solution.size()];
                    for (int i = 0; i < solution.size(); i++) {
                        chain[i] = solution.get(i);
                    }
                    break;
                }
            }
        }

        //Execute the combustion
        int index = line.indexOf(" ");
        String[] args;
        if (index < 0) {
            args = null;
        } else {
            args = line.substring(index + 1).split(" ");
            line = line.substring(0, index);
        }
        try {
            combustions.get(Class.forName(line)).combust(chain, m, args);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Maybe optimize
    private static boolean isValidCombo(Moveable m, List<Class> combo, List<Moveable> solution) {
        if (combo.isEmpty()) {
            return true;
        }
        List<Moveable> success = new ArrayList<Moveable>();
        //Gather all collided Moveables
        List<Moveable> moveables = new ArrayList<Moveable>();
        if (m.getOwner() != null) {
            moveables.add(m.getOwner());
        }
        moveables.addAll(m.getAttachments());
        //Check each for a possible combo
        for (Moveable mo : moveables) {
            if (combo.remove(mo.getClass())) {
                success.add(mo);
                solution.add(mo);
                if (combo.isEmpty()) {
                    return true;
                }
            }
        }

        //Check the successful ones
        for (Moveable mo : success) {
            if (isValidCombo(mo, combo, solution)) {
                return true;
            }
        }
        return false;
    }

    public static Particle getRandomParticle(int x, int y, int sx, int sy) {
        try {
            Particle g = ((Particle) (spawnParticles.get((int) (Math.random() * spawnParticles.size())).newInstance()));
            g.setVars(x, y, sx, sy);
            return g;
        } catch (InstantiationException ex) {
            Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Mechanics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void combust(int x, int y, int radius) {
        GamePanel.combustions.add(new Circle(x, y, radius));
    }

    public static void applyCombustion(int x, int y, int radius, Moveable m) {
        if (m instanceof Combustable && containedInCircle(x, y, radius, m)) {
            combustMoveable(m);
        }
    }

    private static boolean containedInCircle(int x, int y, int radius, Moveable m) {
        int dx = (int) Math.abs(x - m.getX());
        int dy = (int) Math.abs(y - m.getY());

        if (dx > m.getWidth() / 2 + radius) {
            return false;
        }
        if (dy > m.getHeight() / 2 + radius) {
            return false;
        }

        if (dx < m.getWidth() / 2) {
            return true;
        }
        if (dy < m.getHeight() / 2) {
            return true;
        }

        double cornerD = Math.pow(dx - m.getWidth() / 2, 2) + Math.pow(dy - m.getHeight() / 2, 2);
        return cornerD <= Math.pow(radius, 2);
    }

    public static void explode(int x, int y, int force) {
        GamePanel.explosions.add(new Circle(x, y, force));
        GamePanel.overlays.add(new ExplosionOverlay(x, y));
    }

    public static void applyExplosion(int x, int y, int force, Moveable p) {
        final double k = 50000;
        int dx = (int) (x - (p.getX() + p.getWidth() / 2)), dy = (int) (y - (p.getY() + p.getHeight() / 2));
        int distance = (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        if (distance < 3) {
            distance = 3;
        }
        double f = k * force / distance;
        double addedVelocity = ((f / p.getMass()) * (20 / 1000.0));
        double angle = Math.atan2(dy, dx);
        p.setSpeedX((float) (p.getSpeedX() - Math.cos(angle) * addedVelocity));
        p.setSpeedY((float) (p.getSpeedY() - Math.sin(angle) * addedVelocity));
    }

    public static boolean checkRotatedCollision(Rectangle r1, double a1, Rectangle r2, double a2) {
        double distance = .5 * Math.sqrt(Math.pow(r1.getWidth(), 2) + Math.pow(r1.getHeight(), 2));
        double dy = distance*Math.sin(a1);
        double dx = distance*Math.cos(a1);
        return false;
    }
    
//    public static int[] getInterval(Rectangle r, double a, int y){
//        //Gather data
//        int rx = (int)r.getX(), ry = (int)r.getY(), width = (int)r.getWidth(), height = (int)r.getHeight();
//        int cx = rx+width/2, cy = ry+height/2;
//        Point[] points = {new Point(rx,ry),new Point(rx+width,ry), new Point(rx,ry+height),new Point (rx+width,ry+height)};
//        //Rotate points
//        for(Point p: points){
//        }
//    }
}
