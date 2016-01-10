package yam.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import yam.blocks.entity.TileEntityLaunchPad;
import yam.blocks.entity.TileEntityTrashCan;
import yam.blocks.entity.render.TileEntityLaunchPadRenderer;
import yam.blocks.entity.render.TileEntityTrashCanRenderer;
import yam.blocks.entity.render.TrashCanRenderHelper;
import yam.entity.EntityAmplifyBomb;
import yam.entity.EntityAmplislime;
import yam.entity.EntityBullet;
import yam.entity.EntityDerek;
import yam.entity.EntityDerekJr;
import yam.entity.EntityFly;
import yam.entity.EntityHalfplayer;
import yam.entity.EntityHiddenReptile;
import yam.entity.EntityLollipopper;
import yam.entity.EntityMLGArrow;
import yam.entity.EntityMissile;
import yam.entity.EntityMummy;
import yam.entity.EntityNukeMissile;
import yam.entity.EntityNukePrimed;
import yam.entity.EntityPsychoPig;
import yam.entity.EntityRainbot;
import yam.entity.EntityRainbowGolem;
import yam.entity.EntitySparkle;
import yam.entity.EntitySparklingDragon;
import yam.entity.EntityTheRotting;
import yam.entity.EntityTick;
import yam.entity.EntityUnicorn;
import yam.entity.model.ModelDerek;
import yam.entity.model.ModelDerekJr;
import yam.entity.model.ModelFly;
import yam.entity.model.ModelGiantMissile;
import yam.entity.model.ModelHiddenReptile;
import yam.entity.model.ModelLollipopper;
import yam.entity.model.ModelMissile;
import yam.entity.model.ModelMummy;
import yam.entity.model.ModelRainbot;
import yam.entity.model.ModelSparklingDragon;
import yam.entity.model.ModelTheRotting;
import yam.entity.model.ModelTick;
import yam.entity.model.ModelUnicorn;
import yam.entity.render.RenderAmplifyBomb;
import yam.entity.render.RenderAmplislime;
import yam.entity.render.RenderBullet;
import yam.entity.render.RenderDerek;
import yam.entity.render.RenderDerekJr;
import yam.entity.render.RenderFly;
import yam.entity.render.RenderHalfplayer;
import yam.entity.render.RenderHiddenReptile;
import yam.entity.render.RenderLollipopper;
import yam.entity.render.RenderMLGArrow;
import yam.entity.render.RenderMissile;
import yam.entity.render.RenderMummy;
import yam.entity.render.RenderNukeMissile;
import yam.entity.render.RenderNukePrimed;
import yam.entity.render.RenderPsychoPig;
import yam.entity.render.RenderRainbot;
import yam.entity.render.RenderRainbowGolem;
import yam.entity.render.RenderSparkle;
import yam.entity.render.RenderSparklingDragon;
import yam.entity.render.RenderTheRotting;
import yam.entity.render.RenderTick;
import yam.entity.render.RenderUnicorn;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
	
	@Override
    public void registerRenderInformation()
    {
        TileEntityRendererChestHelper.instance = new TrashCanRenderHelper();
    }

    @Override
    public void registerTileEntitySpecialRenderer()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrashCan.class, new TileEntityTrashCanRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new TileEntityLaunchPadRenderer());
    }
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityHalfplayer.class, new RenderHalfplayer(new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, new RenderMissile(new ModelMissile()));
		RenderingRegistry.registerEntityRenderingHandler(EntityNukePrimed.class, new RenderNukePrimed(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeMissile.class, new RenderNukeMissile(new ModelGiantMissile()));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntitySparkle.class, new RenderSparkle());
		RenderingRegistry.registerEntityRenderingHandler(EntityAmplifyBomb.class, new RenderAmplifyBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityPsychoPig.class, new RenderPsychoPig(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMummy.class, new RenderMummy(new ModelMummy(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFly.class, new RenderFly(new ModelFly(), 0.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDerek.class, new RenderDerek(new ModelDerek(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDerekJr.class, new RenderDerekJr(new ModelDerekJr(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTick.class, new RenderTick(new ModelTick(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTheRotting.class, new RenderTheRotting(new ModelTheRotting(), 0.75F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHiddenReptile.class, new RenderHiddenReptile(new ModelHiddenReptile(), 0.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityLollipopper.class, new RenderLollipopper(new ModelLollipopper(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbowGolem.class, new RenderRainbowGolem());
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbot.class, new RenderRainbot(new ModelRainbot(), 0.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAmplislime.class, new RenderAmplislime(new ModelSlime(16), new ModelSlime(0), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnicorn.class, new RenderUnicorn(new ModelUnicorn(), 0.75F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMLGArrow.class, new RenderMLGArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntitySparklingDragon.class, new RenderSparklingDragon(new ModelSparklingDragon(), 0.75F));
	}

}