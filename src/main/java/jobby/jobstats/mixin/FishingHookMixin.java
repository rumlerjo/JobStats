package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Shadow public abstract Player getPlayerOwner();
    
    // Shadow the internal nibble timer
    @Shadow private int nibble;

    @Inject(method = "retrieve", at = @At("HEAD"))
    private void onFishCaught(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        Player player = this.getPlayerOwner();
        
        // If nibble > 0 when retrieve is called, they successfully reeled in a catch
        if (player != null && !player.level().isClientSide() && this.nibble > 0) {
            ItemStack rod = player.getMainHandItem();
            if (StatManager.isTrackable(rod)) {
                StatManager.incrementStat(rod, StatType.FISH_CAUGHT, 1);
                StatManager.setOwnership(rod, player.getName().getString());
            }
        }
    }
}