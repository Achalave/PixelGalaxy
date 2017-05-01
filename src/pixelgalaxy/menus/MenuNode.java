package pixelgalaxy.menus;

import java.awt.*;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import pixelgalaxy.Art;

public class MenuNode implements MouseListener, MouseMotionListener {

    public static ArrayList<ArrayList<Area>> animations;
    public static Point mouse;
    public static Font font;
    public static Image backImage;
    public TexturePaint paint;
    public static String shownName;
    private MenuNode topNode;
    private MenuNode parentNode;
    //For movement of all
    float speedX, speedY, percisionX, percisionY;
    static float speedAllX, speedAllY, percisionAllX, percisionAllY;
    static int shiftAllX, shiftAllY;
    //for movement of current node
    int x, y, destX, destY, passes;
    boolean passed = false;
    Point stageOne = null;
    //The frames for animation
    ArrayList<Area> areas;
    //The current finished frame to be drawn
    Area base;
    //The index of the current frame for this node
    int index;
    //Determines the length and quality of the animation
    static final int time = 3; //time for half the animation(it's then reversed)
    static final int fps = 60;
    static float timeBetween = 1.0f / fps;
    //Used to determine when to switch frames
    long timeElapsed = 0;
    //Index increment   Used externally Used internally      Has been selected
    boolean increasing, newArea = true, changedArea = false, active = false;
    //Connections to nodes that require a visual merge
    List<MenuNode> connections;
    //The children of this node
    ArrayList<MenuNode> children;
    MenuNode activeChild = null;
    List<Integer> shiftX;
    List<Integer> shiftY;
    String name;
    MenuNode connection = null;
    Image icon;
    float shrink = 0;
    boolean shrinkingChildren = false;

    public MenuNode(String name, MenuNode pn, MenuNode tn, String icon) {
        this.icon = Art.images.get(icon);

        BufferedImage texture = (BufferedImage) Art.images.get("/menu/Cell2.png");
        paint = new TexturePaint(texture, new Rectangle2D.Double(Math.random() * texture.getWidth(), Math.random() * texture.getHeight(), texture.getWidth(null), texture.getHeight(null)));

        if (font == null) {
            font = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
        }

        if (backImage == null) {
            backImage = Art.images.get("/menu/Back.png");
        }

        connections = Collections.synchronizedList(new ArrayList<MenuNode>());
        children = new ArrayList<MenuNode>();
        shiftX = Collections.synchronizedList(new ArrayList<Integer>());
        shiftY = Collections.synchronizedList(new ArrayList<Integer>());

        this.name = name;

        topNode = tn;
        parentNode = pn;
    }

    //Creates all the frames for animation
    public void setup() {
        if (animations == null) {
            animations = new ArrayList<ArrayList<Area>>();
            int num = 10;//Total number of animations to make
            for (int i = 0; i < num; i++) {
                animations.add(generateAnimation());
            }
        }
        areas = new ArrayList<Area>();
        for (Area a : animations.get((int) (animations.size() * Math.random()))) {
            areas.add((Area) a.clone());
        }
        for (int i = 0; i < areas.size(); i++) {
            shiftX.add(0);
            shiftY.add(0);
        }
        refreshBase();
    }

    public ArrayList<Area> generateAnimation() {
        ArrayList<Area> areas = new ArrayList<Area>();
        int size = (int) (Math.random() * 30 + 100);
        Area base = new Area(new Ellipse2D.Double(0 - size / 2, 0 - size / 2, size, size));
        //Make the base look more natural
        int num = 20;
        double angle = 0;
        for (int i = 0; i < num; i++) {
            angle += Math.random() * Math.PI / 8 + Math.PI / 10;
            int x = (int) (Math.cos(angle) * size / 2);
            int y = (int) (Math.sin(angle) * size / 2);
            int s = (int) (Math.random() * 10 + 8);
            base.add(new Area(new Ellipse2D.Double(x - s / 2, y - s / 2, s, s)));
        }

        //Create random sub circles
        ArrayList<SubCircle> subs = new ArrayList<SubCircle>();
        num = (int) (7 + Math.random() * 9);
        double ainc = 2 * Math.PI / num;
        angle = Math.random() * Math.PI;
        for (int i = 0; i < num; i++) {
            //Generate random stats
            int s = (int) (Math.random() * 20 + size / 4);
            float dr = (float) (Math.random() * 7 - 5);
            double da = Math.random() * (Math.PI / 20) * ((Math.random() * 2 >= 1) ? -1 : 1);
            int r = (int) ((Math.random() * -s / 4) + size / 2);
            subs.add(new SubCircle(s, angle, r, da, dr));
            //Increment the angle with some randomization
            angle += ainc + (Math.random() * Math.PI / 20 - Math.PI / 40);
        }
        //Create the frames (Areas)
        num = fps * time; //number of frames
        for (int i = 0; i < num; i++) {
            Area frame = (Area) (base.clone());
            for (SubCircle sub : subs) {
                frame.add(sub.getArea());
                sub.tic(timeBetween);
            }
            areas.add(frame);
            shiftX.add(0);
            shiftY.add(0);
        }
        return areas;
    }

    public void addChild(MenuNode n) {
        children.add(n);
    }

    public void draw(Graphics2D g) {

        //Share the draw if nessesary
        if (active || shrinkingChildren) {
            for (MenuNode n : children) {
                n.draw(g);
            }
        }
        //Apply shifting for the current frame if needed
        if (changedArea) {
            refreshBase();
            changedArea = false;
        } else {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).hasNewArea()) {
                    refreshBase();
                    break;
                }
            }
        }
        if (connection == null) {
            g.setPaint(paint);
            g.fill(base);
            g.setColor(Color.BLUE);
            g.draw(base);
            drawOverlay(g);
            this.drawOverlaysInChain(this, g);
        }
        if (topNode == null && shownName != null) {
            drawName(g);
        }

    }

    public void drawOverlaysInChain(MenuNode node, Graphics g) {
        for (int i = 0; i < node.connections.size(); i++) {
            node.connections.get(i).drawOverlay(g);
            drawOverlaysInChain(node.connections.get(i), g);
        }
    }

    public void drawOverlay(Graphics g) {
        if (activeChild == null && (parentNode == null || parentNode.activeChild == null || parentNode.activeChild == this)) {
            Image draw;
            if (active && topNode != null) {
                draw = backImage;
            } else {
                draw = icon;
            }
            if (draw == null) {
                return;
            }
            g.drawImage(draw, x - draw.getWidth(null) / 2, y - draw.getHeight(null) / 2, null);
        }
    }

    public void drawName(Graphics2D g) {
        String print = shownName;
        g.setFont(font);
        FontMetrics mets = g.getFontMetrics();
        Rectangle2D r = mets.getStringBounds(print, g);
        int buffer = 4;
        int height = (int) (r.getHeight() + buffer);
        r.setRect(mouse.x, mouse.getY() - height, r.getWidth() + buffer, height);
        g.setColor(new Color(0, 0, 0, 150));
        g.fill(r);
        g.setColor(new Color(255, 255, 255, 150));
        g.draw(r);
        g.drawString(print, (int) (r.getX() + buffer / 2), (int) (r.getY() + buffer / 2 + 2 * (r.getHeight() / 3)));
    }

    public void applyShift(int index) {
        if (shiftX.size() > index && (shiftX.get(index) != 0 || shiftY.get(index) != 0)) {
            AffineTransform af = new AffineTransform();
            af.translate(shiftX.get(index), shiftY.get(index));
            areas.get(index).transform(af);
            shiftX.set(index, 0);
            shiftY.set(index, 0);
            changedArea = true;
        }
    }

    public void refreshBase() {
        if (shrink != 0) {
            Area a = areas.get(index);
            Rectangle r = a.getBounds();
            AffineTransform af = new AffineTransform();
            //af.translate(-(r.x+r.width/2),-(r.y+r.height/2));
            af.scale(shrink, shrink);
            af.translate((r.x*(1/shrink)+r.width/2*(1/shrink))-(r.x+r.width/2),
                    (r.y*(1/shrink)+r.height/2*(1/shrink))-(r.y+r.height/2));
            base = a.createTransformedArea(af);
        } else {
            base = areas.get(index);
        }

        if (!connections.isEmpty()) {
            base = (Area) base.clone();
            for (int i = 0; i < connections.size(); i++) {
                base.add(connections.get(i).getArea());
            }
        }
    }

    public void tic(long millis) {
        timeElapsed += millis;
        if (!isLoaded()) {
            return;
        }
        //if in the process of shrinking
        if (shrink != 0) {
            final float shrinkRate = .8f;
            shrink -= (millis / 1000.0) * shrinkRate;
            changedArea = true;
        }
        if (shrinkingChildren) {
            boolean go = true;
            for (MenuNode n : children) {
                if (n.shrink > .05) {
                    go = false;
                    break;
                }
            }
            if (go) {
                for (MenuNode n : children) {
                    for (int i = 0; i < n.connections.size(); i++) {
                        n.connections.get(i).connection = null;
                    }
                    n.connections.clear();
                }
                shrinkingChildren = false;
            }
        }
        //if everything needs to be shifted (only done by top node)
        if (topNode == null && (speedAllX != 0 || speedAllY != 0)) {
            percisionAllY += (speedAllY * (millis / 1000.0));
            percisionAllX += (speedAllX * (millis / 1000.0));
            int dx = (int) percisionAllX;
            int dy = (int) percisionAllY;
            shiftAll(dx, dy);
            shiftAllX -= dx;
            shiftAllY -= dy;
            percisionAllX -= dx;
            percisionAllY -= dy;

            if ((speedAllX == 0 || shiftAllX == 0 || speedAllX / Math.abs(speedAllX) != shiftAllX / Math.abs(shiftAllX))
                    && (speedAllY == 0 || shiftAllY == 0 || speedAllY / Math.abs(speedAllY) != shiftAllY / Math.abs(shiftAllY))) {
                speedAllX = 0;
                speedAllY = 0;
                shiftAll(shiftAllX, shiftAllY);
                shiftAllX = 0;
                shiftAllY = 0;
            }
        }
        //if movement is nessesary
        if (speedX != 0 || speedY != 0) {
            //Apply the speed
            percisionX += speedX * (millis / 1000.0);
            percisionY += speedY * (millis / 1000.0);
            //Apply any stored movement of integer ammount
            shift((int) percisionX, (int) percisionY);
            percisionX = percisionX - (int) percisionX;
            percisionY = percisionY - (int) percisionY;
        }

        //if the frame needs to be changed
        if (timeElapsed / 1000.0 >= timeBetween) {
            newArea = true;
            changedArea = true;
            timeElapsed -= (int) (timeBetween * 1000);
            if (index >= areas.size() - 1) {
                increasing = false;
            }
            if (index <= 0) {
                increasing = true;
            }
            if (increasing) {
                applyShift(index + 1);
                index++;
            } else {
                applyShift(index - 1);
                index--;
            }
        } else {
            applyShift(index);
        }

        //Finish movement
        if (speedX != 0 || speedY != 0) {
            //Change speed if no longer with the parent
            if (stageOne != null) {
                Area one = areas.get(index);
                if (!one.contains(stageOne)) {
                    stageOne = null;
                    setSpeedFor(destX, destY, Math.random() * 50 + 175);
                }
            } else {
                //Adjust the speed if passed the dest
                int dx = destX - x;
                int dy = destY - y;
                //Called only once per pass
                if (!passed && (speedX == 0 || dx == 0 || speedX / Math.abs(speedX) != dx / Math.abs(dx))
                        && (speedY == 0 || dy == 0 || speedY / Math.abs(speedY) != dy / Math.abs(dy))) {
                    passes++;
                    passed = true;
                }

                if (passed) {
                    //If its slowed down enough then flip directions
                    //System.out.println(Math.abs(speedX)+Math.abs(speedY));
                    if (Math.abs(speedX) < 100 / passes && Math.abs(speedY) < 100 / passes) {
                        if (passes >= 5) {
                            speedX = 0;
                            speedY = 0;
                            shift(destX - x, destY - y);
                        } else {
                            setSpeedFor(destX, destY, (Math.random() * 50 + 250) / passes);
                        }
                        passed = false;
                    } //Slow the node down
                    else {
                        final float pps = 8f;//percent per seccond
                        speedX -= speedX * pps * (millis / 1000.0);
                        speedY -= speedY * pps * (millis / 1000.0);
                    }
                }
            }
        }

        //Share the tic if nessesary
        if (active || shrinkingChildren) {
            for (MenuNode n : children) {
                n.tic(millis);
            }
        }
        refreshConnections();
    }

    final public void shift(int dx, int dy) {
        x += dx;
        y += dy;
//        Rectangle2D r = paint.getAnchorRect();
//        r.setRect(r.getX()+dx, r.getY()+dy, r.getWidth(), r.getHeight());
//        r.setRect(x, y, r.getWidth(), r.getHeight());
        //Change all the shifts
        for (int i = 0; i < areas.size(); i++) {
            shiftX.set(i, shiftX.get(i) + dx);
            shiftY.set(i, shiftY.get(i) + dy);
        }

    }

    public void refreshConnections() {
        if (speedX == 0 && speedY == 0) {
            boolean go = false;
            for (int i = 0; i < connections.size(); i++) {
                if (nodesCollide(this, connections.get(i))) {
                    go = true;
                }
            }
            if (!go) {
                return;
            }
        }
        //Check if connections need to be made
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).connection = null;
        }
        connections.clear();
        if (topNode != null) {
            topNode.collectIntersections(connections, this);
        }
        changedArea = true;
    }

    public void shiftAll(int dx, int dy) {
        if (isLoaded()) {
            shift(dx, dy);
            destX += dx;
            destY += dy;
            if (stageOne != null) {
                stageOne.x = stageOne.x + dx;
                stageOne.y = stageOne.y + dy;
            }
            if (active || shrinkingChildren) {
                for (MenuNode n : children) {
                    n.shiftAll(dx, dy);
                }
            }
        }
    }

    public void collectIntersections(List<MenuNode> nodes, MenuNode collector) {
        if (collector.connection != this && nodesCollide(this, collector)) {
            MenuNode owner = this;
            while (owner.connection != null) {
                owner = owner.connection;
            }
            if (owner != collector && !inConnectionChain(owner, collector)) {
                nodes.add(owner);
                owner.connection = collector;
            }
        }
        if (active) {
            for (MenuNode n : children) {
                n.collectIntersections(nodes, collector);
            }
        }
    }

    public boolean inConnectionChain(MenuNode chain, MenuNode test) {
        for (int i = 0; i < chain.connections.size(); i++) {
            MenuNode node = chain.connections.get(i);
            if (node == test) {
                return true;
            }
            if (inConnectionChain(node, test)) {
                return true;
            }
        }
        return false;
    }

    public boolean nodesCollide(MenuNode one, MenuNode two) {
        if (two.index >= two.areas.size() || one.index >= one.areas.size()) {
            return false;
        }
        Rectangle r = one.areas.get(one.index).getBounds();
        Rectangle r2 = two.areas.get(two.index).getBounds();
        return r.intersects(r2);
    }

    public void moveTo(int x, int y) {
        passed = false;
        passes = 0;
        destX = x;
        destY = y;
        percisionX = 0;
        percisionY = 0;
        setSpeedFor(x, y, Math.random() * 10 + 35);
        //Find the point that will leave the parent last
        double slopeY;
        if ((x - this.x) == 0) {
            slopeY = (y - this.y);
            slopeY /= Math.abs(slopeY);
        } else {
            slopeY = (y - this.y) / (x - this.x);
        }
        double slopeX;
        if ((y - this.y) == 0) {
            slopeX = (x - this.x);
            slopeX /= Math.abs(slopeX);
        } else {
            slopeX = (x - this.x) / (y - this.y);
        }

        double ex = this.x;
        double ey = this.y;
        do {
            ex += slopeX;
            ey += slopeY;
        } while (areas.get(index).contains(ex, ey));
        stageOne = new Point((int) (ex), (int) (ey));
    }

    private void setSpeedFor(int x, int y, double speed) {
        double angle = Math.atan2(x - this.x, y - this.y);
        speedX = (float) (Math.sin(angle) * speed);
        speedY = (float) (Math.cos(angle) * speed);
    }

    public void setLocation(int x, int y) {
        shift(x - this.x, y - this.y);
    }

    public void addConnection(MenuNode n) {
        connections.add(n);
        connections.addAll(n.getConnections());
    }

    public boolean hasNewArea() {
        return newArea;
    }

    public boolean isLoaded() {
        return areas != null;
    }

    public Area getArea() {
        newArea = false;
        return base;
    }

    public List<MenuNode> getConnections() {
        return connections;
    }

    public void clearMinorData() {
        connections.clear();
        activeChild = null;
        active = false;
        shrink = 0;
    }

    public void clearMemory() {
        clearMinorData();
        areas.clear();
        shiftX.clear();
        shiftY.clear();
    }

    public void activate() {
        if (!active) {
            shrinkingChildren = false;
            //If still moving finish now
            if (speedX != 0 || speedY != 0) {
                speedX = 0;
                speedY = 0;
                percisionX = 0;
                percisionY = 0;
                setLocation(destX, destY);
            }
            double angle = 3 * Math.PI / 2;
            double da = 2 * Math.PI / children.size();
            int radius = (int) base.getBounds().getWidth() + 100;
            for (MenuNode n : children) {
                //Load unloaded children
                if (!n.isLoaded()) {
                    n.setup();
                } else {
                    n.clearMinorData();
                }
                //Launch the children out and set locations
                n.setLocation(this.x, this.y);
                int x = (int) (Math.cos(angle) * radius) + this.x;
                int y = (int) (Math.sin(angle) * radius) + this.y;
                n.moveTo(x, y);
                angle += da;
            }
            //Shift to this node
            if (topNode != null) {
                shiftAllX += parentNode.x - this.x;
                shiftAllY += parentNode.y - this.y;
                double speed = (Math.random() * 50 + 350);
                angle = Math.atan2(shiftAllX, shiftAllY);
                speedAllX = (float) (Math.sin(angle) * speed);
                speedAllY = (float) (Math.cos(angle) * speed);
            }
            active = true;
        }
    }

    public void deactivate() {
        active = false;
        //get rid of connections of children
        for (MenuNode n : children) {
            n.shrink = .999f;
            n.speedX = 0;
            n.speedY = 0;
        }
        shrinkingChildren = true;
        //Shift to parent node
        if (parentNode != null) {
            shiftAllX += this.x - parentNode.x;
            shiftAllY += this.y - parentNode.y;
            double speed = (Math.random() * 50 + 350);
            double angle = Math.atan2(shiftAllX, shiftAllY);
            speedAllX = (float) (Math.sin(angle) * speed);
            speedAllY = (float) (Math.cos(angle) * speed);
        }
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (active) {
            for (MenuNode n : children) {
                n.mouseClicked(me);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (active) {
            for (MenuNode n : children) {
                n.mousePressed(me);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (active) {
            //Focus on only that child
            if (activeChild != null) {
                activeChild.mouseReleased(me);
                //If it is no longer active
                if (!activeChild.isActive()) {
                    activeChild = null;
                    activate();
                }
            } //Go back a level
            else if (topNode != null && base.contains(me.getPoint())) {
                deactivate();
            } else {//Pass the click to all children
                for (MenuNode n : children) {
                    n.mouseReleased(me);
                    //The child was activated
                    if (n.isActive()) {
                        activeChild = n;
                    }
                }
            }
        } else if (base.contains(me.getPoint()) && stageOne == null) {
            activate();
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (active) {
            for (MenuNode n : children) {
                n.mouseEntered(me);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (active) {
            for (MenuNode n : children) {
                n.mouseExited(me);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (active) {
            for (MenuNode n : children) {
                n.mouseDragged(me);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (topNode == null) {
            mouse = me.getPoint();
        }
        if (active) {
            for (MenuNode n : children) {
                n.mouseMoved(me);
            }
            if (shownName != null && shownName.equals(name)) {
                shownName = null;
            }
        } else if (parentNode != null && parentNode.activeChild == null && base != null && base.contains(me.getPoint())) {
            shownName = name;
        } else if (shownName != null && shownName.equals(name)) {
            shownName = null;
        }
    }
}

class SubCircle {

    Area area;
    int x, y;
    double speedAngle;
    float speedRadius;
    double angle;
    float radius;

    public SubCircle(int size, double a, int radius, double da, float dr) {
        this.radius = radius;
        speedAngle = da;
        speedRadius = dr;
        angle = a;
        x = (int) (this.radius * Math.cos(angle));
        y = (int) (this.radius * Math.sin(angle));
        area = new Area(new Ellipse2D.Double(x - size / 2, y - size / 2, size, size));
    }

    public void tic(float sec) {
        angle += speedAngle * sec;
        radius += speedRadius * sec;
        int newX = (int) (radius * Math.cos(angle));
        int newY = (int) (radius * Math.sin(angle));
        AffineTransform af = new AffineTransform();
        af.translate(newX - x, newY - y);
        x = newX;
        y = newY;
        area.transform(af);
    }

    public Area getArea() {
        return area;
    }
}