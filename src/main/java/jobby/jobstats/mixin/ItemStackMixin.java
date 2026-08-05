package jobby.jobstats.mixin;

import jobby.jobstats.stat.StatManager;
import jobby.jobstats.stat.StatType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V", at = @At("HEAD"))
    private void onHurtAndBreak(int damage, ServerLevel level, ServerPlayer player, Consumer<Item> onBreak, CallbackInfo ci) {
        if (player != null && !level.isClientSide()) {
            ItemStack stack = (ItemStack) (Object) this;
            
            if (StatManager.isTrackable(stack)) {
                // Filter: Check if the item is armor (Equippable with Max Damage) or a Shield
                boolean isArmorOrShield = (stack.has(DataComponents.EQUIPPABLE) && stack.has(DataComponents.MAX_DAMAGE)) || stack.is(Items.SHIELD);
                
                if (isArmorOrShield) {
                    // Replaced DAMAGE_TAKEN with your exact enum naming: ARMOR_DAMAGE
                    StatManager.incrementStat(stack, StatType.ARMOR_DAMAGE, damage);
                }
                
                // We still want to track ownership when tools take damage, so this remains outside the filter
                StatManager.setOwnership(stack, player.getName().getString());
            }
        }
    }
}