package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    
    // Inject directly into the dedicated critical hit method instead of using LocalCapture
    @Inject(method = "crit", at = @At("HEAD"))
    private void onAttackCrit(Entity entityHit, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        
        // Ensure we are only tracking this on the server
        if (!player.level().isClientSide()) {
            ItemStack weapon = player.getMainHandItem();
            
            if (StatManager.isTrackable(weapon)) {
                StatManager.incrementStat(weapon, StatType.CRITICAL_STRIKES, 1);
            }
        }
    }
}