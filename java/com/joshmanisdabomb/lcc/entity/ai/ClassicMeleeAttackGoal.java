package com.joshmanisdabomb.lcc.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

public class ClassicMeleeAttackGoal extends MeleeAttackGoal {

    private double targetX;
    private double targetY;
    private double targetZ;
    private double speed;
    private long targetTimer;

    public ClassicMeleeAttackGoal(CreatureEntity entity, double speed) {
        super(entity, speed, false);
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        long i = this.attacker.world.getGameTime();
        if (i - this.targetTimer < 20L) {
            return false;
        } else {
            this.targetTimer = i;
            LivingEntity livingentity = this.attacker.getAttackTarget();
            return livingentity != null && livingentity.isAlive();
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else {
            return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
        }
    }

    @Override
    public void startExecuting() {
        this.attacker.setAggroed(true);
    }

    @Override
    public void resetTask() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        if (!EntityPredicates.CAN_AI_TARGET.test(livingentity)) {
            this.attacker.setAttackTarget(null);
        }

        this.attacker.setAggroed(false);
    }

    @Override
    public void tick() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        this.attacker.getLookController().setLookPositionWithEntity(livingentity, 30.0F, 30.0F);

        this.targetX = livingentity.posX;
        this.targetY = livingentity.getBoundingBox().minY;
        this.targetZ = livingentity.posZ;
        this.attacker.getMoveHelper().setMoveTo(this.targetX, this.targetY, this.targetZ, this.speed);
        if (this.attacker.collidedHorizontally) this.attacker.getJumpController().setJumping();

        this.attackTick = Math.max(this.attackTick - 1, 0);

        double d0 = this.attacker.getDistanceSq(livingentity.posX, livingentity.getBoundingBox().minY, livingentity.posZ);
        this.checkAndPerformAttack(livingentity, d0);
    }

}
