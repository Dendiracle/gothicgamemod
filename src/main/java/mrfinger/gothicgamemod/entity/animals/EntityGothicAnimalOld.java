package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.util.GGMTicks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public abstract class EntityGothicAnimalOld extends EntityMob {
	
	public static final List<EntityGothicAnimalOld.AnimalsPack> animalPacksList = new LinkedList<EntityGothicAnimalOld.AnimalsPack>();
		
	//public static List<List> animalsListsList = new ArrayList();
	
	//public static List<Class> animalClassID = new ArrayList();
	
	AnimalsPack 			pack;
		
	protected int 			agrrAnimTimer,
							aggrAnimationsPassed,
							timerToRecall;	
	
	protected boolean 		herdAnimal;

	public EntityGothicAnimalOld(World world, boolean herdAnimal) {
		super(world);
		this.herdAnimal = herdAnimal;
		/*if (herdAnimal) {
			
			AnimalsPack pack = EntityGothicAnimalOld.getClosestPack(this);
			
			if (pack == null || pack.getDistancetoEntity(this) > 96.0F) {
				pack = new AnimalsPack(world, null, this.posX, this.posY, this.posZ);
			}
			
			this.pack = pack;
		}*/
	}
	
	@Override
	public void onLivingUpdate() {		
		super.onLivingUpdate();
    }
	
	@Override
	protected void applyEntityAttributes() {		
	  super.applyEntityAttributes();
	  this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getAMovementSpeed());
	  this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.getAKnockbackresistance());
	  this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getAMovementSpeed());
	  this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(this.getAKnockbackresistance());
	  this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.getAFollowRange()); 
	  
	  this.setSize(this.getAWidth(), this.getAHeight());
	}
	
	/*@Override
	protected Entity findPlayerToAttack() {
				
		EntityPlayer annoyingPlayer = this.annoyingPlayer;		
		
		if (annoyingPlayer == null) {			
			annoyingPlayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, this.aggrAnimsDist);
			if (annoyingPlayer != null && this.getDistanceToEntity(annoyingPlayer) <= this.aggrDist) {				
				return annoyingPlayer;
			}
			else this.annoyingPlayer = annoyingPlayer;
		}
		
		else if (this.stareTimer <= 0 && this.getDistanceToEntity(annoyingPlayer) >= this.aggrAnimsDist) {
			if (this.getDistanceToEntity(annoyingPlayer) >= this.getAFollowRange()) {
				this.annoyingPlayer = null;
				return null;
			}
			
			this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getAMovementSpeed());
			if (this.canBeCalled) {
				this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, annoyingPlayer, 100F, true, false, false, true));
			}
			
			if (this.herdAnimal) {
        		this.sayAboutAnnoyer(annoyingPlayer);        		
    		}
		}
        
		else {
			this.farToAnnoyingPlayer = false;
			this.setPathToEntity(null);
			
        	if (this.aggrAnimationsPassed < this.maxAggAnimations) { 
        		
        		this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
        		this.faceEntity(annoyingPlayer, 360F, 360F);
        		
        		if (this.stareTimer == 0) {        			
        			this.playSound(this.aggrSound(), 1.0F, 1.0F);
        		}
        		
        		this.stareTimer++;
        		//System.out.println("   " + this.stareTimer);
        		        		
        		if (this.herdAnimal) {
        			
        			this.sayAboutAnnoyer(annoyingPlayer);
        		}  
        		
        		EntityPlayer closestAnnoyer =  this.worldObj.getClosestVulnerablePlayerToEntity(this, this.aggrDist);
        		
        		if (closestAnnoyer != null) {
        			this.toAttackAfterAnim = closestAnnoyer;
        		}
        		
        		if (this.stareTimer >= this.maxStareTimer) {         			      			
        			this.stareTimer = 0;
        			this.updateBodySwing();
        			
        			if (this.getDistanceToEntity(annoyingPlayer) > this.aggrAnimsDist || !this.canEntityBeSeen(annoyingPlayer) || this.toAttackAfterAnim != null && this.toAttackAfterAnim.entityAlive() || this.annoyingPlayer.isEntityInvulnerable() || !this.annoyingPlayer.entityAlive()) {
                		this.aggrAnimationsPassed = 0;
                		this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getAMovementSpeed());
                		this.farToAnnoyingPlayer = true;
                		this.annoyingPlayer = annoyingPlayer = null;
                		this.setPathToEntity(null);
                		return null;
                	} 
        			
        			this.aggrAnimationsPassed++; 
        			
        		}    
        		
        		this.updateBodySwing();        		
        		
        	}
        	else {
        		this.annoyingPlayer = null;
        		this.aggrAnimationsPassed = 0;
        		this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getAMovementSpeed());
        		this.farToAnnoyingPlayer = true;        		
        		return annoyingPlayer;
        	}        
		}
		return null;
	}*/	
	
	@Override
	protected Entity findPlayerToAttack() {
		return null;
	}
	
	@Override
	protected boolean isMovementCeased() {
		if	(this.agrrAnimTimer > 0 || this.aggrAnimationsPassed > 0) return true;
		else return false;
    }
	
	/*@Override
	public boolean attackEntityAsMob(Entity p_70652_1_) {
		this.playSound(this.attackSound(), 1.0F, 1.0F);
		return super.attackEntityAsMob(p_70652_1_);
	}*/	
		
	public int getStareTimer() {
		return this.agrrAnimTimer;
	}	
	
	abstract protected String aggrSound();
	
	abstract protected String attackSound();
		
	protected float getAMaxHealth() {
		return 10.0F;
	}
	
	protected float getAAttackDamage() {
		return 1.0F;
	}
	
	protected float getAMovementSpeed() {
		return 1.0F;
	}
	
	protected float getAKnockbackresistance() {
		return 0F;
	}
	
	protected float getAFollowRange() {
		return 16.0F;
	}
	
	protected float getAWidth() {
		return 0.5F;
	}
	
	protected float getAHeight() {
		return 0.5F;
	}
	
	
	public static void onUpdatePacks() {

		if (GGMTicks.getGTicks() % 20 == 0) {
			
			for(AnimalsPack pack : EntityGothicAnimalOld.animalPacksList) {
				pack.everySecUpdate();
			}
		}
		
	}
	
	public static EntityGothicAnimalOld.AnimalsPack getClosestPack(Entity entity) {
		
		AnimalsPack p = null;
		
		float d = 32768.0F;
		
		for (AnimalsPack pack : EntityGothicAnimalOld.animalPacksList) {
			
			float dd = pack.getDistancetoEntity(entity);
			
			if (dd < d && pack.fraction.alliesSet.contains(entity.getClass())) {
				d = dd;
				p = pack;
			}
		}
		
		return p;
	}
	
	
		
	protected class AnimalsPack {
		
		
		
		List<EntityGothicAnimalOld>	animalsList;
		
		protected final World 		world;
		
		protected Fraction 	fraction;
		
		protected double 			coordX,
									coordY,
									coordZ;
		
		protected float				radW,
									height,
									aggrRad;
		
		protected int				stareTimer,
									toAggrTimer = 5;
		
		protected final AxisAlignedBB aabb;
		
		protected boolean			aggr;
		
		EntityLivingBase 			entityAnnoyer;
		
		
		AnimalsPack(World world, Fraction fraction, double x, double y, double z) {
			
			this.world = world;			
			//if (fraction == null) this.fraction = GGMEntities.emptyFraction;
			//else this.fraction = fraction;
			
			this.coordX = x;
			this.coordY = y;
			this.coordZ = z;
			
			this.aabb = AxisAlignedBB.getBoundingBox(this.coordX - this.aggrRad, this.coordY, this.coordZ - this.aggrRad, this.coordX + this.aggrRad, this.coordY + this.height, this.coordZ + this.aggrRad);
			
			EntityGothicAnimalOld.animalPacksList.add(this);
		}
		
		
		
		void onUpdate() {
			
		}
		
		void everySecUpdate() {
			
			if (this.aggr) {
				for (EntityGothicAnimalOld entity : this.animalsList) {
					entity.entityToAttack = this.entityAnnoyer;
				}
			}

			else if (this.entityAnnoyer != null) {
				
				float d = this.getDistanceOnPlane(this.entityAnnoyer);
				
				if (d < this.radW || (d < this.aggrRad && ++this.stareTimer >= this.toAggrTimer)) {
					
					this.aggr = true;
				}			
				
				else {
					this.entityAnnoyer = null;
					this.stareTimer = 0;
				}
			}
			
			else {
				this.findEntityToAttack();
			}
			
		}
		
		
		
		protected void findEntityToAttack() {
			
			List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.aabb);
			
			List<EntityGothicAnimalOld> list2 = this.animalsList;
			
			if (list2 != null) list.removeAll(list2);
			
			EntityLivingBase closestEntity = null;					
			
			float d = this.aggrRad;
			
			for (EntityLivingBase entity : list) {
				float dd = this.getDistanceOnPlane(entity);
				if (dd < d && this.checkVisionable(entity) && this.fraction.enemiesSet.contains(entity.getClass())) {
					d = dd;
					closestEntity = entity;
				}
			}
			
			if (closestEntity != null) {
				
				this.entityAnnoyer = closestEntity;				
				
				if (d < this.radW) {
					this.aggr = true;
				}
			}
		}
		
		boolean checkVisionable(Entity entity) {
			for (EntityGothicAnimalOld gEntity : this.animalsList) {
				if (gEntity.canEntityBeSeen(entity)) return true;
			}
			return false;
		}
		
		public float getDistanceOnPlane(Entity p_70032_1_) {
	        double f = this.coordX - p_70032_1_.posX;
	        double f1 = this.coordY - p_70032_1_.posY;
	        return MathHelper.sqrt_float((float) (f * f + f1 * f1));
	    }
		
		public float getDistancetoEntity(Entity p_70032_1_) {
	        double f = this.coordX - p_70032_1_.posX;
	        double f1 = this.coordY - p_70032_1_.posY;
	        double f2 = this.coordZ - p_70032_1_.posZ;
	        return MathHelper.sqrt_float((float) (f * f + f1 * f1 + f2 * f2));
	    }
		
		
		
		public void setPackSize(float rad, float height) {
			this.radW = rad;
			this.height = height;
			this.aabb.maxY = this.coordY + height;
		}
		
		public void setAggrRad(float aggrRad) {
			this.aggrRad = aggrRad;
			this.aabb.minX = this.coordX - aggrRad;
			this.aabb.minZ = this.coordZ - aggrRad;
			this.aabb.maxX = this.coordX + aggrRad;
			this.aabb.maxZ = this.coordZ + aggrRad;
		}
		
		
		
		public void setPackCoords(double x, double y, double z) {
			this.coordX = x;
			this.coordY = y;
			this.coordZ = z;
			this.aabb.setBounds(x - this.radW, y, z - this.radW, x + this.radW, y + this.height, z + this.radW);
		}
		
		public void movePack(double x, double y, double z) {
			this.setPackCoords(this.coordX + x, this.coordY + y, this.coordZ + z);
		}
		
	}		

}
