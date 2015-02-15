package yam;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class CustomDamage {

    public static final DamageSource breath = (new DamageSource("breath")).setDamageBypassesArmor();
    public static final DamageSource nuke = (new DamageSource("nuke")).setExplosion().setDamageBypassesArmor();
    public static final DamageSource nuclear = (new DamageSource("nuclear")).setDamageBypassesArmor();
    public static final DamageSource blaster = (new DamageSource("blaster"));
    public static final DamageSource rainsplosion = (new DamageSource("rainsplosion")).setExplosion();
	public static final DamageSource rainbowStream = (new DamageSource("rStream")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
    public static final DamageSource mlg = (new DamageSource("mlg")).setExplosion().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
    public static final DamageSource spikes = (new DamageSource("spikes"));
    public static final DamageSource cactus2 = (new DamageSource("cactus2")).setDamageBypassesArmor();
	
    public static DamageSource causeBlasterDamage(Entity par0Entity, Entity par1Entity) {
    	return (new EntityDamageSourceIndirect("blaster", par0Entity, par1Entity));
	}
    
    public static DamageSource causeRainsplosionDamage(Entity par0Entity, Entity par1Entity) {
        return (new EntityDamageSourceIndirect("rainsplosion", par0Entity, par1Entity));
	}
    
    public static DamageSource causeRektDamage(Entity par0Entity, Entity par1Entity) {
        return (new EntityDamageSourceIndirect("mlg", par0Entity, par1Entity));
	}

}
