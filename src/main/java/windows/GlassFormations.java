/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.util.Random;

/**
 *
 * @author jbannon
 */
public class GlassFormations {
    
    public class GlassOffset {
        private final int x, y, z;
        
        public GlassOffset(final int x,
                           final int y,
                           final int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public int getX() { return this.x; }
        public int getY() { return this.y; }
        public int getZ() { return this.z; }
    }

    private final Random rand;
    
    private final GlassOffset[] ConstantCheck = {
       new GlassOffset(1, 0, 0), new GlassOffset(-1, 0, 0),
       new GlassOffset(0, 1, 0), new GlassOffset(0, -1, 0),
       new GlassOffset(0, 0, 1), new GlassOffset(0, 0, -1)
    };

    private final GlassOffset[][] RandomCheck = {
        {
          new GlassOffset(1, 1, 0), new GlassOffset(-1, -1, 0),
          new GlassOffset(0, 1, 1), new GlassOffset(0, -1, -1)  
        },
        {
          new GlassOffset(1, -1, 0), new GlassOffset(-1, 1, 0),
          new GlassOffset(0, -1, 1), new GlassOffset(0, 1, -1)  
        },
        {
          new GlassOffset(1, 1, 0), new GlassOffset(-1, 1, 0),
          new GlassOffset(0, 1, 1), new GlassOffset(0, 1, -1)  
        },
        {
          new GlassOffset(1, -1, 0), new GlassOffset(-1, -1, 0),
          new GlassOffset(0, -1, 1), new GlassOffset(0, -1, -1)  
        },
        {
          new GlassOffset(-1, 1, 0), new GlassOffset(-1, -1, 0),
          new GlassOffset(0, 1, -1), new GlassOffset(0, -1, -1)  
        },
        {
          new GlassOffset(1, 1, 0), new GlassOffset(1, -1, 0),
          new GlassOffset(0, 1, 1), new GlassOffset(0, -1, 1)  
        }
    };
    
    public GlassFormations()
    {
        rand = new Random();
    }
    
    public GlassOffset[] getConstantCheck()
    {
        return this.ConstantCheck;
    }
    
    public GlassOffset[] getRandomCheck()
    {
        return RandomCheck[rand.nextInt(6)];
    }
}
