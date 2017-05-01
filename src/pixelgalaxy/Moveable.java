package pixelgalaxy;

import devices.ThreadArray;
import devices.ThreadIterator;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Moveable {

    //Basic movement
    protected int x, y;
    protected float speedX, speedY, percisionX, percisionY;
    //Attatched moveables (particles/entities)
    protected ThreadArray<Moveable> attachments;
    protected Moveable owner;
    //The total mass
    protected int mass;
    //Bounds for all combined Moveable
    Rectangle bounds;
    protected float temperature;

    public Moveable() {
        attachments = new ThreadArray<Moveable>();
        mass = getMass();
        temperature = getStartingTemperature();
    }

    public Moveable(int x, int y, int sx, int sy) {
        this();
        this.x = x;
        this.y = y;
        this.speedX = sx;
        this.speedY = sy;
    }

    public void tic(long millis) {
        //Movement is only handled by the top Moveable
        if (owner == null) {
            //Add the speed
            percisionX += speedX * (millis / 1000.0);
            percisionY += speedY * (millis / 1000.0);
            //Move everything the needed ammount
            int dx = (int) percisionX;
            int dy = (int) percisionY;
            percisionX -= dx;
            percisionY -= dy;
            shiftLocation(dx, dy);
        }
        //Send tics on and conduct heat
        ThreadIterator atch = (ThreadIterator) attachments.listIterator();
        while (atch.hasNext()) {
            Moveable m = (Moveable) atch.next();
            Mechanics.conductHeat(this, m, millis);
            m.tic(millis);
        }
        atch.dispose();
    }

    public void attach(Moveable m) {
        attachments.add(m);
        mass += m.getTotalMass();
        m.owner = (this);
    }

    public boolean detatch(Moveable m) {
        boolean valid = m.getOwner() == this && attachments.remove(m);
        if (valid) {
            m.owner = (null);
            mass -= m.getTotalMass();
        }
        return valid;
    }

    public void becomeFree() {
        if (owner != null) {
            owner.detatch(this);
        } else {
            GamePanel.pieces.remove(this);
        }
        ThreadIterator<Moveable> mi = (ThreadIterator<Moveable>) attachments.listIterator();
        while (mi.hasNext()) {
            detatch(mi.next());
        }
        mi.dispose();
    }

    //Shifts everything using percision; takes effect on the next tic
    public void shift(float x, float y) {
        this.percisionX += x;
        this.percisionY += y;
    }

    //Must be overwritten and most call the super draw
    public void draw(Graphics g) {
        ThreadIterator atch = (ThreadIterator) attachments.listIterator();
        while (atch.hasNext()) {
            ((Moveable) atch.next()).draw(g);
        }
        atch.dispose();
    }

    public Moveable getMoveableAt(Point p) {
        if (getBounds().contains(p)) {
            return this;
        } else {
            ThreadIterator atch = (ThreadIterator) attachments.listIterator();
            while (atch.hasNext()) {
                Moveable m = ((Moveable) atch.next()).getMoveableAt(p);
                if (m != null) {
                    atch.dispose();
                    return m;
                }
            }
            atch.dispose();
        }
        return null;
    }

    public Moveable[] getCollision(Moveable m) {
        if (m.getSectionTotalBounds().intersects(getSectionTotalBounds())) {
            //check the tops
            if (getBounds().intersects(m.getBounds())) {
                Moveable[] end = {this, m};
                return end;
            } else {
                //Do this on m.attatchments collisions
                ThreadIterator<Moveable> atch;
                if (!m.attachments.isEmpty()) {
                    atch = (ThreadIterator) m.getAttachments().listIterator();
                    while (atch.hasNext()) {
                        Moveable ma = atch.next();
                        if (getBounds().intersects(ma.getBounds())) {
                            atch.dispose();
                            Moveable[] end = {this,ma};
                            return end;
                        }
                    }
                    atch.dispose();
                }
                //do iteration to check for remaining collisions with recursion
                atch = (ThreadIterator) attachments.listIterator();
                while (atch.hasNext()) {
                    Moveable ma = atch.next();
                    Moveable[] end = ma.getCollision(m);
                    if(end != null){
                        atch.dispose();
                        return end;
                    }
                    ThreadIterator<Moveable> atch2 = (ThreadIterator) m.getAttachments().listIterator();
                    while (atch2.hasNext()) {
                        end = (ma).getCollision((Moveable) atch2.next());
                        if (end != null) {
                            atch.dispose();
                            atch2.dispose();
                            return end;
                        }
                    }
                    atch2.dispose();
                }
                atch.dispose();
            }
        }
        return null;
    }

    public Rectangle getTotalBounds() {
        if (owner != null) {
            return (owner.getTotalBounds());
        }
        return getSectionTotalBounds();
    }

    public Rectangle getSectionTotalBounds() {
        Rectangle r = getBounds();
        if (!attachments.isEmpty()) {
            ThreadIterator atch = (ThreadIterator) attachments.listIterator();
            while (atch.hasNext()) {
                Moveable m = (Moveable) atch.next();
                r.add((m).getSectionTotalBounds());
            }
            atch.dispose();

        }
        return r;
    }

    public void addToMass(int m) {
        mass += m;
    }

    //Abstract Methods
    public abstract Rectangle getBounds();

    public abstract double getHeatCoeficient();

    protected abstract int getStartingTemperature();

    public abstract int getMass();

    //Basic Getters and Setters
    public void setVars(float x, float y, float sx, float sy) {
        this.percisionX = x;
        this.percisionY = y;
        this.speedX = sx;
        this.speedY = sy;
    }

    public float getSpeedX() {
        if (owner == null) {
            return speedX;
        } else {
            return owner.getSpeedX();
        }
    }

    public void setSpeedX(float speedX) {
        if (owner == null) {
            this.speedX = speedX;
        } else {
            owner.setSpeedX(speedX);
        }
    }

    //Recurse to the top
    public float getSpeedY() {
        if (owner == null) {
            return speedY;
        } else {
            return owner.getSpeedY();
        }
    }

    public void setSpeedY(float speedY) {
        if (owner == null) {
            this.speedY = speedY;
        } else {
            owner.setSpeedY(speedY);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setSpeed(float sx, float sy) {
        setSpeedX(sx);
        setSpeedY(sy);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setLocation(int x, int y) {
        int dx = x - this.x;
        int dy = y - this.y;
        shiftLocation(dx, dy);
    }

    public void shiftLocation(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        ThreadIterator atch = (ThreadIterator) attachments.listIterator();
        while (atch.hasNext()) {
            ((Moveable) atch.next()).shiftLocation(dx, dy);
        }
        atch.dispose();
    }

    public int getTotalMass() {
        if (owner == null) {
            return mass;
        } else {
            return owner.getTotalMass();
        }
    }

    public ThreadArray<Moveable> getAttachments() {
        return attachments;
    }

    public int getWidth() {
        return (int) getBounds().getWidth();
    }

    public int getHeight() {
        return (int) getBounds().getHeight();
    }

    public Moveable getOwner() {
        return owner;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": x-" + x + " y-" + y;
    }
}
