
package pixelgalaxy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Art {
    
    public static HashMap<String,Image> images;
    public static final String artPath = "/resources/images";
    
    public static void loadImages(){
        images = new HashMap<String,Image>();
        addImagesFromPath("/");
                
        int s;
        BufferedImage temp;
        Graphics g;
        
        
        s=20;
        temp = new BufferedImage(s,s,BufferedImage.SCALE_FAST);
        g = temp.getGraphics();
        g.setColor(new Color(145,182,255));
        g.fillRect(0, 0, s, s);
        images.put("/particles/Oxygen.png", temp);
        
        s=20;
        temp = new BufferedImage(s,s,BufferedImage.SCALE_FAST);
        g = temp.getGraphics();
        g.setColor(new Color(178,255,145));
        g.fillRect(0, 0, s, s);
        images.put("/particles/Hydrogen.png", temp);
        
        s=20;
        temp = new BufferedImage(s,s,BufferedImage.SCALE_FAST);
        g = temp.getGraphics();
        g.setColor(new Color(65,183,183));
        g.fillRect(0, 0, s, s);
        images.put("/particles/Helium.png", temp);
        
        s=20;
        temp = new BufferedImage(s,s,BufferedImage.SCALE_FAST);
        g = temp.getGraphics();
        g.setColor(new Color(240,35,227));
        g.fillRect(0, 0, s, s);
        images.put("/particles/Silicon.png", temp);
        
//        s=20;
//        temp = new BufferedImage(s,s,BufferedImage.SCALE_FAST);
//        g = temp.getGraphics();
//        g.setColor(new Color(255,255,255,125));
//        g.fillRect(0, 0, s, s);

    }
    
    private static void addImagesFromPath(String path){
        InputStream is = Art.class.getResourceAsStream(artPath+path);
        if(is == null){
            return;
        }
        Scanner scan = new Scanner(is);
        while(scan.hasNext()){
            String p = scan.nextLine();
            Image i = Mechanics.loadImage(artPath+path+p);
            if(i == null){
                addImagesFromPath(path+p+"/");
            }else{
                images.put(path+p, i);
            }
        }
    }
}
