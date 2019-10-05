package com.joshmanisdabomb.lcc.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public class LCCEntitySpawnPacket implements LCCPacket {

    private final ResourceLocation type;
    private final double posX, posY, posZ;
    private final double motionX, motionY, motionZ;
    private final float pitch, yaw;
    private final int id;
    private final UUID uuid;

    public LCCEntitySpawnPacket(ResourceLocation type, int id, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float pitch, float yaw, UUID uuid) {
        this.type = type;
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.pitch = pitch;
        this.yaw = yaw;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.uuid = uuid;
    }

    public LCCEntitySpawnPacket(Entity entity) {
        this(EntityType.getKey(entity.getType()), entity.getEntityId(), entity.posX, entity.posY, entity.posZ, entity.getMotion().x, entity.getMotion().y, entity.getMotion().z, entity.getPitchYaw().x, entity.getPitchYaw().y, entity.getUniqueID());
    }

    public static void encode(LCCEntitySpawnPacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.type);
        buf.writeInt(msg.id);
        buf.writeDouble(msg.posX).writeDouble(msg.posY).writeDouble(msg.posZ);
        buf.writeDouble(msg.motionX).writeDouble(msg.motionY).writeDouble(msg.motionZ);
        buf.writeFloat(msg.pitch).writeFloat(msg.yaw);
        buf.writeUniqueId(msg.uuid);
    }

    public static LCCEntitySpawnPacket decode(PacketBuffer buf) {
        return new LCCEntitySpawnPacket(buf.readResourceLocation(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readUniqueId());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        ClientWorld world = Minecraft.getInstance().world;
        EntityType.byKey(type.toString()).ifPresent(t -> {
            Entity e = t.create(world);

            e.setPosition(posX, posY, posZ);
            e.setMotion(motionX, motionY, motionZ);
            e.rotationPitch = pitch;
            e.rotationYaw = yaw;

            world.addEntity(id, e);
        });
    }

}