package mrfinger.gothicgamemod.entity.capability.abilities;

/*@SideOnly(Side.CLIENT)
public class MeleeFightClientPlayer extends MeleeFight {
		
	
	
	Minecraft minecraft = Minecraft.getMinecraft();
	
	public int 		toFightingStanceTicks = 5,
					lMCounter;
	
	public boolean 	inFightingStance,
					willHit;
	
	
	
	public MeleeFightClientPlayer(EntityPlayer entity) {
		super(entity);
	}
	
	
	
	@Override
	public void inLivingUpdate() {
		
		
	}
		
	public void inClientTicksRun() {	
		
		
		if (this.hitTimer > 0) {
			this.hitTimer--;
		}
		
		if (this.statsEntity.isCastingSpell()) {
			
		}

		else if (minecraft.currentScreen == null || minecraft.currentScreen.allowUserInput) {
			
			if (minecraft.gameSettings.keyBindAttack.getIsKeyPressed()) {
				
				if (this.inFightingStance) {
					if (this.hitTimer == 0) {						
						if (minecraft.objectMouseOver != null && SwitchMovingObjectType.field_152390_a[this.minecraft.objectMouseOver.typeOfHit.ordinal()] == 1) {
							if (Minecraft.getMinecraft().gameSettings.keyBindLeft.getIsKeyPressed()) {
								this.minecraft.thePlayer.swingItem();
								this.sideHit(this.minecraft.objectMouseOver.entityHit);
							}
							else if (Minecraft.getMinecraft().gameSettings.keyBindRight.getIsKeyPressed()) {
								this.minecraft.thePlayer.swingItem();
								this.sideHit(this.minecraft.objectMouseOver.entityHit);
							}
							else if (Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed()) {
								this.minecraft.thePlayer.swingItem();
								this.directHit(this.minecraft.objectMouseOver.entityHit);
							}
							this.hitTimer = 20;
						}
					}
				}
				
				else if (minecraft.objectMouseOver != null && SwitchMovingObjectType.field_152390_a[minecraft.objectMouseOver.typeOfHit.ordinal()] == 2){					

                    int i = minecraft.objectMouseOver.blockX;
                    int j = minecraft.objectMouseOver.blockY;
                    int k = minecraft.objectMouseOver.blockZ; 
                    
                    if (minecraft.theWorld.getBlock(i, j, k).getMaterial() == Material.air) {
    					
    					this.lMCounter++;
    					this.willHit = true;
    					if (this.lMCounter >= this.toFightingStanceTicks) {
    						
    						System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA 22222222222222222");
    						this.fightingStanceOn();
    						this.willHit = false;
    					}                    	
                    }
                    
                    else minecraft.playerController.clickBlock(i, j, k, minecraft.objectMouseOver.sideHit);
                    
				}
				
				else {
					
					this.lMCounter++;
					this.willHit = true;
					if (this.lMCounter >= this.toFightingStanceTicks) {
						
						System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA 1111111111111");
						this.fightingStanceOn();
						this.willHit = false;
					}
				}
			}
			
			else if (this.willHit) {
				
				if (this.hitTimer == 0) {
					this.minecraft.thePlayer.swingItem();
					if (minecraft.objectMouseOver != null && SwitchMovingObjectType.field_152390_a[this.minecraft.objectMouseOver.typeOfHit.ordinal()] == 1) {				
						this.directHit(this.minecraft.objectMouseOver.entityHit);
					}
					this.hitTimer = 20;
				}
				
				this.lMCounter = 0;
				this.willHit = false;
			}
			
			else {
				this.lMCounter = 0;
				if (this.inFightingStance) {
					this.fightingStanceOff();
					
				}
			}
		}
		
		else {
			this.lMCounter = 0;
			if (this.inFightingStance) {
				this.fightingStanceOff();
				
			}
		}
	}
	
	
	
	@Override
	public void fightingStanceOn() {
		super.fightingStanceOn();		
		Packet.createPacket(Packet.sFightingStanceOn).sendToServer();
	}

	@Override
	public void fightingStanceOff() {
		super.fightingStanceOff();
		Packet.createPacket(Packet.sFightingStanceOff).sendToServer();
	}
	
	
	
	@Override
	public void directHit(Entity target) {
		this.minecraft.playerController.attackEntity(this.minecraft.thePlayer, target);
	}
	
	@Override
	public void sideHit(Entity target) {
		this.minecraft.playerController.attackEntity(this.minecraft.thePlayer, target);
	}
	
		
	
	
	
	@SideOnly(Side.CLIENT)
	static final class SwitchMovingObjectType {
        static final int[] field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];
        private static final String __OBFID = "CL_00000638";

        static
        {
            try
            {
                field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }

}*/
