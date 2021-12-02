package mrfinger.gothicgamemod.util;

public abstract class GGMTicks {
	
	
	
    public static int 			gTicks;
    public static final int		standartTicks = 5;
    
    
    
    public static void fireTick() {
    	GGMTicks.gTicks++;
    }
    
    public static int getGTicks() {
    	return GGMTicks.gTicks;
    }
    
}
