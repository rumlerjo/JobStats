package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "useOn", at = @At("RETURN"))
    private void jobstats$onLogStripped(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        // If the return value is a successful interaction, the block was definitively stripped (or scraped)
        if (cir.getReturnValue().consumesAction() && !context.getLevel().isClientSide()) {
            ItemStack stack = context.getItemInHand();
            
            if (StatManager.isTrackable(stack)) {
                StatManager.incrementStat(stack, StatType.LOGS_STRIPPED, 1);
            }
        }
    }
}