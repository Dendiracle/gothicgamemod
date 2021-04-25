package mrfinger.gothicgamemod.util;

import codechicken.lib.packet.PacketCustom;

public class Packet {
	
	public static final int 	ToPlayer = 1,
								cExpSinch = 2,
								cStatSinch = 3,
								cSkillSinch = 4,
								cNullifyGui = 10;
	
	public static final int 	sUpgrade = 1,
								sAbilitySwitch = 5,
								sFightingStanceOn = 11,
								sFightingStanceOff = 12;
    
		
	public static PacketCustom createPacket(int t){
        return new PacketCustom("Keks", t);
    }



   
}