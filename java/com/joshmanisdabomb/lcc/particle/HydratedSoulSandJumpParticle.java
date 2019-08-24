package com.joshmanisdabomb.lcc.particle;

import com.joshmanisdabomb.lcc.registry.LCCParticles;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HydratedSoulSandJumpParticle extends MetaParticle {

    private final double initialSpace;
    private final double bubbleHeight;
    private final double ringMotion;

    public HydratedSoulSandJumpParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.initialSpace = xSpeed;
        this.bubbleHeight = ySpeed;
        this.ringMotion = zSpeed;
    }

    public void tick() {
        //ring of bubbles
        for (int i = 0; i < 100; i++) {
            Direction side = Direction.byHorizontalIndex(this.rand.nextInt(4));
            boolean xAxis = side.getXOffset() != 0;
            double x = xAxis ? side.getXOffset() : (this.rand.nextDouble() - 0.5) * 2;
            double z = !xAxis ? side.getZOffset() : (this.rand.nextDouble() - 0.5) * 2;
            Vec3d motion = Vec3d.fromPitchYaw(0, (float)MathHelper.positiveModulo(side.getHorizontalAngle() + ((xAxis ? -z : x) * (side.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 45 : -45)), 360));
            this.world.addOptionalParticle(LCCParticles.hydrated_soul_sand_bubble,
                this.posX + (x * (this.initialSpace / 2)), this.posY + (this.rand.nextDouble() * 0.1), this.posZ + (z * (this.initialSpace / 2)),
                motion.x * this.ringMotion * 0.3, 0.0D, motion.z * this.ringMotion * 0.3);
        }
        //column of bubbles
        for (int i = 0; i < 50; i++) {
            this.world.addOptionalParticle(LCCParticles.hydrated_soul_sand_bubble,
                this.posX + ((rand.nextDouble() - 0.5) * this.initialSpace), this.posY + (this.rand.nextDouble() * 0.1), this.posZ + ((rand.nextDouble() - 0.5) * this.initialSpace),
                0.0D, (rand.nextDouble() * 0.4) + (rand.nextDouble() * this.bubbleHeight * 0.4), 0.0D);
        }
        this.setExpired();
    }

}
