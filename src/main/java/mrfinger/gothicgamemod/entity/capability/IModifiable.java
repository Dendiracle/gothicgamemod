package mrfinger.gothicgamemod.entity.capability;

public interface IModifiable {
	
	
	
	int setModifier(IModifiable.ModifierType type, float value);
	
	void removeModifier(int type);
	
	
	
	IModifiable.ModifierType getModifierType(int value);
	
	float getModifierValue(int value);
	
	
	
	
	
	
	
	public enum ModifierType {
		conc((byte) 0),
		bModif((byte) 1),
		sumModif((byte) 2);		
		
		public final byte id;
		
		private ModifierType(byte id) {
			this.id = id;
		}		
		
	}
	
}
