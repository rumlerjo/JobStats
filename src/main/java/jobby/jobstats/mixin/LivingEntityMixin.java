package jobby.jobstats.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // Target 'hurtServer' instead of 'hurt', and add ServerLevel to the parameters
    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void jobstats$trackDamageDealt(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // If the return value is true, the attack successfully dealt damage
        if (cir.getReturnValue()) {
            
            // Extract the attacker from the damage source
            if (source.getEntity() instanceof ServerPlayer player) {
                ItemStack weapon = player.getMainHandItem();
                
                // Ignore empty hands
                if (!weapon.isEmpty()) {
                    StatManager.incrementStat(weapon, StatType.DAMAGE_DEALT, Math.round(amount));
                }
            }
        }
    }
}