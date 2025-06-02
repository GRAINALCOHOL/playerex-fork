package com.bibireden.playerex.mixin;

import com.bibireden.data_attributes.api.DataAttributesAPI;
import com.bibireden.playerex.api.attribute.PlayerEXAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    @Shadow public abstract void setCritArrow(boolean critical);

    @Shadow public abstract boolean isCritArrow();

    private AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;isCritArrow()Z"))
    private void playerex$onEntityHit(EntityHitResult entityHitResult, CallbackInfo info) {
        if (this.getOwner() instanceof LivingEntity entity) {
            DataAttributesAPI.getValue(PlayerEXAttributes.RANGED_CRITICAL_CHANCE, entity).ifPresent((chance) ->
                this.setCritArrow(false)
            );
        }
    }

    @ModifyArg(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private float playerex$onEntityHit(float original) {
        if (this.getOwner() instanceof LivingEntity entity) {
            final float damage = original;

            boolean isCritical = DataAttributesAPI.getValue(PlayerEXAttributes.RANGED_CRITICAL_CHANCE, entity)
                .map((chance) -> {
                    boolean shouldCritical = entity.getRandom().nextFloat() < chance;
                    this.setCritArrow(shouldCritical);
                    return shouldCritical;
                }
            ).orElse(this.isCritArrow());

            if (isCritical) {
                return DataAttributesAPI.getValue(PlayerEXAttributes.RANGED_CRITICAL_DAMAGE, entity)
                    .map((v) -> (float) (damage * (1.0 + v)))
                    .orElseGet(() -> {
                        final float offset = this.random.nextFloat() * 4 - 2; // [-2, +2]
                        return Math.max(0, Math.min(original + offset, Integer.MAX_VALUE));
                    });
            }
        }
        return original;
    }
}
