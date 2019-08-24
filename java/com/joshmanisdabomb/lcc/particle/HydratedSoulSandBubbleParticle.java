package com.joshmanisdabomb.lcc.particle;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HydratedSoulSandBubbleParticle extends LCCTexturedParticle {

    public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
        new ResourceLocation(LCC.MODID, "textures/particle/hydrated_soul_sand/bubble.png"),
        new ResourceLocation(LCC.MODID, "textures/particle/hydrated_soul_sand/bubble_pop_0.png"),
        new ResourceLocation(LCC.MODID, "textures/particle/hydrated_soul_sand/bubble_pop_1.png"),
        new ResourceLocation(LCC.MODID, "textures/particle/hydrated_soul_sand/bubble_pop_2.png"),
        new ResourceLocation(LCC.MODID, "textures/particle/hydrated_soul_sand/bubble_pop_3.png"),
        new ResourceLocation(LCC.MODID, "textures/particle/hydrated_soul_sand/bubble_pop_4.png"),
    };

    private int frame = 0;

    public HydratedSoulSandBubbleParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(0.02F, 0.02F);
        this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.maxAge = 20 + (int)(Math.random()*20);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.77;
            this.motionY *= 0.77;
            this.motionZ *= 0.77;
            if (this.age >= this.maxAge - 4) frame++;
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURES[frame];
    }

}