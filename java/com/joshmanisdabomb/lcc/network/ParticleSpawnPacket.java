package com.joshmanisdabomb.lcc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleSpawnPacket implements LCCPacket {

    private final IParticleData data;
    private final boolean forceAlwaysRender;
    private final double posX, posY, posZ;
    private final double speedX, speedY, speedZ;

    public ParticleSpawnPacket(IParticleData data, boolean forceAlwaysRender, double posX, double posY, double posZ, double speedX, double speedY, double speedZ) {
        this.data = data;
        this.forceAlwaysRender = forceAlwaysRender;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
    }

    public static void encode(ParticleSpawnPacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.data.getType().getRegistryName());
        buf.writeBoolean(msg.forceAlwaysRender);
        buf.writeDouble(msg.posX).writeDouble(msg.posY).writeDouble(msg.posZ);
        buf.writeDouble(msg.speedX).writeDouble(msg.speedY).writeDouble(msg.speedZ);
        msg.data.write(buf);
    }

    public static ParticleSpawnPacket decode(PacketBuffer buf) {
        ParticleType type = ForgeRegistries.PARTICLE_TYPES.getValue(buf.readResourceLocation());
        boolean forceAlwaysRender = buf.readBoolean();
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        double speedX = buf.readDouble();
        double speedY = buf.readDouble();
        double speedZ = buf.readDouble();
        IParticleData data = type.getDeserializer().read(type, buf);
        return new ParticleSpawnPacket(data, forceAlwaysRender, posX, posY, posZ, speedX, speedY, speedZ);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        Minecraft.getInstance().world.addParticle(data, forceAlwaysRender, posX, posY, posZ, speedX, speedY, speedZ);
    }

}