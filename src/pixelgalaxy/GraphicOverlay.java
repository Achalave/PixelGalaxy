
package pixelgalaxy;

import java.awt.Graphics;

public interface GraphicOverlay {
    public boolean draw(Graphics g);
    public void tic(long millis);
}
