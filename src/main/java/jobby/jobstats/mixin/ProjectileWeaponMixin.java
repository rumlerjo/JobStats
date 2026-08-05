package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BowItem.class, CrossbowItem.class, TridentItem.class})
public abstract class ProjectileWeaponMixin {
    
    @Inject(method = "releaseUsing", at = @At("HEAD"))
    private void onReleaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft, CallbackInfoReturnable<Boolean> cir) {
        if (!level.isClientSide() && StatManager.isTrackable(stack)) {
            if (stack.getItem() instanceof TridentItem) {
                StatManager.incrementStat(stack, StatType.TRIDENTS_THROWN, 1);
            } else {
                StatManager.incrementStat(stack, StatType.ARROWS_SHOT, 1);
            }
        }
    }
}