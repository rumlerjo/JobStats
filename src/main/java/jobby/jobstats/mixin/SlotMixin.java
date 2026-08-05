package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Inject(method = "onTake", at = @At("HEAD"))
    private void onSlotTake(Player player, ItemStack stack, CallbackInfo ci) {
        if (!player.level().isClientSide() && StatManager.isTrackable(stack)) {
            // This covers taking from Chests (Loot), Crafting Results, and Merchant slots
            StatManager.setOwnership(stack, player.getName().getString());
        }
    }
}