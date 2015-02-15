package yam;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class Achieves {

	private static Achievement craftMud;

	private static Achievement wearHazmat;
	private static Achievement pickupUranium;
	private static Achievement craftNuke;
	
	private static AchievementPage global;
	
	public static void loadAchievements() {
		int i = 0;
		
		craftMud = new Achievement("achievement.craftMud","craftMud",0,1,YetAnotherMod.mud,(Achievement)null).registerStat();
		
		wearHazmat = new Achievement("achievement.wearHazmat","wearHazmat",0,-1,YetAnotherMod.hazmatChestplate,(Achievement)null).registerStat();
		pickupUranium = new Achievement("achievement.pickupUranium","pickupUranium",1,-1,YetAnotherMod.uranium,wearHazmat).registerStat();
		craftMud = new Achievement("achievement.craftNuke","craftNuke",2,-1,YetAnotherMod.nuke,pickupUranium).registerStat();

		//Remember to tediously add all new achievements here! :D
    	global = new AchievementPage("Yet Another Mod", new Achievement[]{craftMud, wearHazmat, pickupUranium, craftNuke});
    	AchievementPage.registerAchievementPage(global);
	}
	
}
