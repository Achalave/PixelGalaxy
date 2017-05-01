package pixelgalaxy.collisions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import pixelgalaxy.Mechanics;
import pixelgalaxy.Moveable;

public class RandomCollision implements Collision {

//    @Override
//    public void interact(int ci1, Cluster c1, int ci2, Cluster c2, int pi1, Particle p1, int pi2, Particle p2, String[] args) {
//        ArrayList<Integer> indexes = new ArrayList<Integer>();
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].equals("*")) {
//                indexes.add(i);
//            }
//        }
//        int r;
//        if (indexes.isEmpty()) {
//            r = (int) (Math.random() * args.length);
//        } else {
//            r = (int) (Math.random() * indexes.get(0) - 1);
//        }
//        try {
//            if (r < indexes.size()) {
//                int end;
//                if (r + 1 < indexes.size()) {
//                    end = indexes.get(r + 1);
//                } else {
//                    end = args.length;
//                }
//                args = Arrays.copyOfRange(args, indexes.get(r) + 1, end);
//            }
//            Mechanics.collisions.get(Class.forName(args[r])).interact(ci1, c1, ci2, c2, pi1, p1, pi2, p2, args);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(RandomCollision.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public void interact(Moveable m1, Moveable m2, String[] args) {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("*")) {
                indexes.add(i);
            }
        }
        int r;
        if (indexes.isEmpty()) {
            r = (int) (Math.random() * args.length);
        } else {
            r = (int) (Math.random() * indexes.get(0) - 1);
        }
        try {
            if (r < indexes.size()) {
                int end;
                if (r + 1 < indexes.size()) {
                    end = indexes.get(r + 1);
                } else {
                    end = args.length;
                }
                args = Arrays.copyOfRange(args, indexes.get(r) + 1, end);
            }
            Mechanics.collisions.get(Class.forName(args[r])).interact(m1, m2, args);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RandomCollision.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
